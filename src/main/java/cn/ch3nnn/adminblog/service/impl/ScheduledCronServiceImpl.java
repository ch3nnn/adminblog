package cn.ch3nnn.adminblog.service.impl;

import cn.ch3nnn.adminblog.entity.ScheduledCron;
import cn.ch3nnn.adminblog.mapper.ScheduledCronMapper;
import cn.ch3nnn.adminblog.service.ScheduledCronService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chentong
 */
@Service
public class ScheduledCronServiceImpl extends ServiceImpl<ScheduledCronMapper, ScheduledCron>
        implements ScheduledCronService {

    @Autowired
    ScheduledCronMapper scheduledCronMapper;

    @Override
    public boolean updateByScheduledCron(ScheduledCron scheduledCron) {
        return retBool(scheduledCronMapper.updateByScheduledCron(scheduledCron));
    }
}




