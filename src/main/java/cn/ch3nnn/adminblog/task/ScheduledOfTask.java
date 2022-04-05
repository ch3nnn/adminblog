package cn.ch3nnn.adminblog.task;

import cn.ch3nnn.adminblog.entity.ScheduledCron;
import cn.ch3nnn.adminblog.mapper.ScheduledCronMapper;
import cn.ch3nnn.adminblog.utils.SpringUtils;

/**
 * 获取执行定时任务
 *
 * @Author ChenTong
 * @Date 2021/9/28 14:21
 */
public interface ScheduledOfTask extends Runnable {


    /**
     * 定时任务方法
     */
    void execute();

    /**
     * 实现控制定时任务启用或禁用的功能
     */
    @Override
    default void run() {

        final ScheduledCronMapper scheduledCronMapper = SpringUtils.getBean(ScheduledCronMapper.class);
        final ScheduledCron scheduledCron = scheduledCronMapper.findByCronClass(this.getClass().getName());
        // 任务是禁用状态
        if (scheduledCron.getCode() == 1) {
            execute();
        }

    }
}
