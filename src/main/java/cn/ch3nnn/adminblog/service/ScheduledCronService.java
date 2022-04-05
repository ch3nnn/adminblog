package cn.ch3nnn.adminblog.service;

import cn.ch3nnn.adminblog.entity.ScheduledCron;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface ScheduledCronService extends IService<ScheduledCron> {

    boolean updateByScheduledCron(ScheduledCron scheduledCron);


}
