package cn.ch3nnn.adminblog.service;

import cn.ch3nnn.adminblog.entity.SysLog;
import cn.ch3nnn.adminblog.entity.UserUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chentong
 */
public interface SysLogService extends IService<SysLog> {


    /**
     * 分页查找
     *
     * @param page
     * @return
     */
    Page<SysLog> findListPage(Page<SysLog> page);


    /**
     * 记录操作日志
     *
     * @param args
     */
    void recordAdminLogInfo(HttpServletRequest request, UserUser user, String diffArticleFields);


}
