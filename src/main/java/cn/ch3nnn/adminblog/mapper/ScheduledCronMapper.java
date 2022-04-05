package cn.ch3nnn.adminblog.mapper;

import cn.ch3nnn.adminblog.entity.ScheduledCron;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Entity cn.ch3nnn.adminblog.entity.ScheduledCron
 */
@Mapper
public interface ScheduledCronMapper extends BaseMapper<ScheduledCron> {


    ScheduledCron findByCronClass(String cronClass);

    int updateByScheduledCron(ScheduledCron scheduledCron);

    int updateByCronClass(ScheduledCron scheduledCron);

}




