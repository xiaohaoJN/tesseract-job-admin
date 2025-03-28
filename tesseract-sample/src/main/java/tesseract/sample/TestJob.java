package tesseract.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tesseract.core.annotation.TesseractJob;
import tesseract.core.context.ExecutorContext;
import tesseract.core.handler.JobHandler;

@TesseractJob(triggerName = "testTrigger-1")
@Component
@Slf4j
public class TestJob implements JobHandler {


    @Override
    public void execute(ExecutorContext executorContext) throws Exception {
        throw new Exception("测试失败重试");
    }
}