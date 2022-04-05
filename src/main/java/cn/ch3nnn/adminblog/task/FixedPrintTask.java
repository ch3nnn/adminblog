package cn.ch3nnn.adminblog.task;

import cn.ch3nnn.adminblog.entity.ScheduledCron;
import cn.ch3nnn.adminblog.mapper.ScheduledCronMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Date;

/**
 * @Author ChenTong
 * @Date 2021/9/28 13:44
 */
@Component
public class FixedPrintTask implements ScheduledOfTask {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ScheduledCronMapper scheduledCronMapper;

    private int i;

    @Override
    public void execute() {
        final ScheduledCron scheduledCron = new ScheduledCron();
        try {
            final File file = new File("123");
            final FileInputStream fileInputStream = new FileInputStream(file);
            final int read = fileInputStream.read(new byte[10]);
            System.out.println(read);
            logger.info("thread id:{},DynamicPrintTask execute times:{}", Thread.currentThread().getId(), ++i);
            scheduledCron.setCronClass(this.getClass().getName());
            scheduledCron.setStatus((byte) 1);
            scheduledCron.setLastTime(new Date());
            scheduledCronMapper.updateByCronClass(scheduledCron);
        } catch (Exception e) {
            scheduledCron.setCronClass(this.getClass().getName());
            scheduledCron.setLastTime(new Date());
            scheduledCron.setStatus((byte) 0);
            scheduledCron.setCode((byte) 0);
            scheduledCron.setErrorMsg(Arrays.toString(e.getStackTrace()));
            scheduledCronMapper.updateByCronClass(scheduledCron);

        }

    }
}
