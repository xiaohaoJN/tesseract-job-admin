package admin.core.scheduler;

import admin.core.component.SenderDelegateBuilder;
import admin.core.scanner.ExecutorScanner;
import admin.core.scanner.MissfireScanner;
import admin.core.scheduler.pool.DefaultSchedulerThreadPool;
import admin.core.scheduler.pool.ISchedulerThreadPool;
import admin.entity.TesseractGroup;
import admin.entity.TesseractTrigger;
import admin.service.*;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.util.CollectionUtils;
import tesseract.exception.TesseractException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class TesseractScheduleBoot {
    @Autowired
    private ITesseractTriggerService tesseractTriggerService;

    @Autowired
    private ITesseractExecutorDetailService executorDetailService;

    @Autowired
    private ITesseractJobDetailService tesseractJobDetailService;

    @Autowired
    private ITesseractExecutorService executorService;

    @Autowired
    private ITesseractGroupService groupService;

    @Autowired
    private SenderDelegateBuilder senderDelegateBuilder;

    @Autowired
    @Qualifier("retryEventBus")
    EventBus retryEventBus;

    private static TesseractScheduleBoot tesseractScheduleBoot;

    /**
     * threadlist
     */

    private static final Map<String, SchedulerThread> SCHEDULER_THREAD_MAP = Maps.newHashMap();

    private static final ReentrantReadWriteLock REENTRANT_READ_WRITE_LOCK = new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock.ReadLock READ_LOCK = REENTRANT_READ_WRITE_LOCK.readLock();
    private static final ReentrantReadWriteLock.WriteLock WRITE_LOCK = REENTRANT_READ_WRITE_LOCK.writeLock();
    /**
     * 暂时先共用同一扫描器
     */
    private ExecutorScanner executorScanner;
    private MissfireScanner missfireScanner;

    /**
     * 单线程 不需要加锁
     */
    public void destroy() {
        SCHEDULER_THREAD_MAP.values().forEach(schedulerThread -> {
            schedulerThread.stopThread();
            schedulerThread.getTesseractTriggerDispatcher().stop();
        });

        if (executorScanner != null) {
            executorScanner.stopThread();
        }

        if (missfireScanner != null) {
            missfireScanner.stopThread();
        }
    }

    @EventListener(ContextRefreshedEvent.class)
    public void start() {
        SCHEDULER_THREAD_MAP.values().forEach(schedulerThread -> schedulerThread.startThread());
        if (executorScanner != null) {
            executorScanner.startThread();
        }
        if (missfireScanner != null) {
            missfireScanner.startThread();
        }
    }

    /**
     * 单线程 不需要加锁
     */
    public void init() {
        tesseractScheduleBoot = this;
        //创建调度线程,根据部门进行线程池隔离
        List<TesseractGroup> groupList = groupService.list();
        if (!CollectionUtils.isEmpty(groupList)) {
            groupList.forEach(group -> {
                String groupName = group.getName();
                SCHEDULER_THREAD_MAP.put(groupName, createSchedulerThread(groupName, group.getThreadPoolNum()));
            });
            //创建扫描线程
            executorScanner = new ExecutorScanner(executorDetailService);
            missfireScanner = new MissfireScanner(tesseractTriggerService);
            executorScanner.setDaemon(true);
            missfireScanner.setDaemon(true);
            return;
        }
        log.info("没有调度组");
    }

    /**
     * 创建调度线程
     *
     * @param groupName
     * @param threadPoolNum
     * @return
     */
    private SchedulerThread createSchedulerThread(String groupName, Integer threadPoolNum) {
        SchedulerThread schedulerThread = new SchedulerThread(groupName, createTesseractTriggerDispatcher(groupName, threadPoolNum), tesseractTriggerService);
        schedulerThread.setDaemon(true);
        return schedulerThread;
    }

    /**
     * 创建任务分发器
     *
     * @param groupName
     * @param threadNum
     * @return
     */
    private TesseractTriggerDispatcher createTesseractTriggerDispatcher(String groupName, Integer threadNum) {
        DefaultSchedulerThreadPool threadPool = new DefaultSchedulerThreadPool(threadNum);
        TesseractTriggerDispatcher tesseractTriggerDispatcher = new TesseractTriggerDispatcher();
        tesseractTriggerDispatcher.setGroupName(groupName);
        tesseractTriggerDispatcher.setExecutorDetailService(executorDetailService);
        tesseractTriggerDispatcher.setExecutorService(executorService);
        tesseractTriggerDispatcher.setTesseractJobDetailService(tesseractJobDetailService);
        tesseractTriggerDispatcher.setThreadPool(threadPool);
        tesseractTriggerDispatcher.setSenderDelegate(senderDelegateBuilder.getSenderDelegate());
        tesseractTriggerDispatcher.setRetryEventBus(retryEventBus);
        return tesseractTriggerDispatcher;
    }

    /**
     *
     *
     *   静态工具方法
     *
     *
     *
     */


    /**
     * 删除组线程池并停止
     *
     * @param
     */
    public static void deleteGroupScheduler(TesseractGroup tesseractGroup) {
        WRITE_LOCK.lock();
        try {
            SchedulerThread schedulerThread = SCHEDULER_THREAD_MAP.remove(tesseractGroup.getName());
            if (schedulerThread == null) {
                log.error("找不到组:{} SchedulerThread", tesseractGroup);
                throw new TesseractException("找不到SchedulerThread");
            }
            schedulerThread.stopThread();
        } finally {
            WRITE_LOCK.unlock();
        }
        log.info("删除组调度器{}成功,删除结果:{}", tesseractGroup, SCHEDULER_THREAD_MAP);
    }

    /**
     * 增加组线程池
     *
     * @param
     */
    public static void addGroupScheduler(TesseractGroup tesseractGroup) {
        WRITE_LOCK.lock();
        try {
            String groupName = tesseractGroup.getName();
            SchedulerThread schedulerThread = tesseractScheduleBoot.createSchedulerThread(groupName, tesseractGroup.getThreadPoolNum());
            schedulerThread.startThread();
            SCHEDULER_THREAD_MAP.put(groupName, schedulerThread);
        } finally {
            WRITE_LOCK.unlock();
        }
        log.info("添加组调度器{}成功,添加结果:{}", tesseractGroup, SCHEDULER_THREAD_MAP);
    }

    /**
     * 执行触发器
     *
     * @param groupName
     * @param tesseractTriggerList
     */
    public static void executeTrigger(String groupName, List<TesseractTrigger> tesseractTriggerList) {
        READ_LOCK.lock();
        SchedulerThread schedulerThread = SCHEDULER_THREAD_MAP.get(groupName);
        try {
            if (schedulerThread == null) {
                log.error("找不到组:{} SchedulerThread", groupName);
                throw new TesseractException("找不到SchedulerThread");
            }
            TesseractTriggerDispatcher tesseractTriggerDispatcher = schedulerThread.getTesseractTriggerDispatcher();
            tesseractTriggerDispatcher.dispatchTrigger(tesseractTriggerList, true);
        } finally {
            READ_LOCK.unlock();
        }
    }

    /**
     * 更新执行线程池大小
     *
     * @param groupName
     * @param threadNum
     */
    public static void updateThreadNum(String groupName, Integer threadNum) {
        READ_LOCK.lock();
        SchedulerThread schedulerThread = SCHEDULER_THREAD_MAP.get(groupName);
        try {
            if (schedulerThread == null) {
                log.error("找不到组:{} SchedulerThread", groupName);
                throw new TesseractException("找不到SchedulerThread");
            }
            ISchedulerThreadPool threadPool = schedulerThread.getTesseractTriggerDispatcher().getThreadPool();
            threadPool.changeSize(threadNum);
        } finally {
            READ_LOCK.unlock();
        }
    }
}
