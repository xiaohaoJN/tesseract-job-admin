package tesseract.sample;

import org.springframework.stereotype.Component;
import tesseract.core.annotation.TesseractJob;
import tesseract.core.context.ExecutorContext;
import tesseract.core.handler.JobHandler;

import java.util.Random;

@TesseractJob(triggerName = "testTrigger-1")
@Component
public class TestJob implements JobHandler {


    @Override
    public void execute(ExecutorContext executorContext) throws Exception {
        System.out.println("任务执行开始");
        Thread.sleep( 1000);
        Random random = new Random(100);
        int i = random.nextInt(100);
        System.out.println(i);
        if (i > 10) {
            Thread.sleep(3 * 1000);
        } else {
            throw new Exception("添加失败任务，测试重试功能");
        }
        System.out.println("任务执行结束");
    }
}
