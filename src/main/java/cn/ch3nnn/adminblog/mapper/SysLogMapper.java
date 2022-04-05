package cn.ch3nnn.adminblog.mapper;

import cn.ch3nnn.adminblog.entity.SysLog;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author mayn
 * @Entity cn.ch3nnn.adminblog.entity.SysLog
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {


    /**
     * @param page
     * @param wrapper
     * @return
     */
    Page<SysLog> selectLogPage(Page<SysLog> page, @Param(Constants.WRAPPER) Wrapper<SysLog> wrapper);
}




