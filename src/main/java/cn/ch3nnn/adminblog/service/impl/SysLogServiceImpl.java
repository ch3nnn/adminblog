package cn.ch3nnn.adminblog.service.impl;

import cn.ch3nnn.adminblog.entity.SysLog;
import cn.ch3nnn.adminblog.entity.UserUser;
import cn.ch3nnn.adminblog.mapper.SysLogMapper;
import cn.ch3nnn.adminblog.service.SysLogService;
import cn.ch3nnn.adminblog.utils.IPUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author chentong
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog>
        implements SysLogService {

    @Autowired
    SysLogMapper sysLogMapper;


    @Override
    public Page<SysLog> findListPage(Page<SysLog> page) {
        final Page<SysLog> sysLogPage = sysLogMapper.selectLogPage(page, null);
        // 前段展示参数限制50长度
        for (SysLog sysLog : sysLogPage.getRecords()) {
            if (sysLog.getParams() != null && sysLog.getParams().length() > 50) {
                sysLog.setParams(sysLog.getParams().substring(0, 50) + "......");
            }
        }
        return sysLogPage;
    }

    @Override
    public void recordAdminLogInfo(HttpServletRequest request, UserUser user, String diffArticleFields) {
        final SysLog sysLog = new SysLog();
        sysLog.setMethod(request.getMethod());
        sysLog.setUsername(user.getUsername());
        sysLog.setOperation("修改文章");
        sysLog.setCreateDate(new Date());
        sysLog.setIp(IPUtils.getIpAddr(request));
        sysLog.setParams(diffArticleFields);
        save(sysLog);
    }
}




