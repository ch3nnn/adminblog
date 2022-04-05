package cn.ch3nnn.adminblog.mapper;

import cn.ch3nnn.adminblog.entity.AdminLog;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author chentong
 * @Entity cn.ch3nnn.adminblog.entity.AdminLog
 */
@Mapper
public interface AdminLogMapper extends BaseMapper<AdminLog> {


    /**
     * @param page
     * @param wrapper
     * @return
     */
    IPage<AdminLog> selectAdminLogPage(Page<AdminLog> page, @Param(Constants.WRAPPER) Wrapper<AdminLog> wrapper);
}




