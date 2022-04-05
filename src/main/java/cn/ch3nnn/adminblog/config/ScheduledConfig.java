package cn.ch3nnn.adminblog.config;

import cn.ch3nnn.adminblog.entity.ScheduledCron;
import cn.ch3nnn.adminblog.mapper.ScheduledCronMapper;
import cn.ch3nnn.adminblog.task.ScheduledOfTask;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 定时任务配置类
 *
 * @Author ChenTong
 * @Date 2021/9/28 14:04
 */
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ScheduledCronMapper scheduledCronMapper;

    @Override
    public void configureTasks(@NotNull ScheduledTaskRegistrar taskRegistrar) {
        final List<ScheduledCron> scheduledCronList = scheduledCronMapper.selectList(null);
        for (ScheduledCron springScheduledCron : scheduledCronList) {
            Object task;
            try {
                Class<?> clazz = Class.forName(springScheduledCron.getCronClass());
                task = context.getBean(clazz);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("tb_scheduled_cron表数据" + springScheduledCron.getCronClass() + "有误", e);
            } catch (BeansException e) {
                throw new IllegalArgumentException(springScheduledCron.getCronClass() + "未纳入到spring管理", e);
            }
            Assert.isAssignable(ScheduledOfTask.class, task.getClass(), "定时任务类必须实现ScheduledOfTask接口");
            // 可以通过改变数据库数据进而实现动态改变执行周期
            taskRegistrar.addTriggerTask(((Runnable) task),
                    triggerContext -> {
                        // 获取定时任务表达式
                        String cronExpression = scheduledCronMapper.findByCronClass(springScheduledCron.getCronClass()).getCronExpression();
                        return new CronTrigger(cronExpression).nextExecutionTime(triggerContext);
                    }
            );
        }
    }

    @Bean
    public ScheduledExecutorService taskExecutor() {
        return Executors.newScheduledThreadPool(10);
    }
}