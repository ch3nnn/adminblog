package cn.ch3nnn.adminblog.aspect;

import cn.ch3nnn.adminblog.common.annotation.Log;
import cn.ch3nnn.adminblog.entity.SysLog;
import cn.ch3nnn.adminblog.entity.UserUser;
import cn.ch3nnn.adminblog.service.SysLogService;
import cn.ch3nnn.adminblog.utils.IPUtils;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Objects;

/**
 * aop 记录管理员操作动作
 *
 * @Author ChenTong
 * @Date 2021/9/22 16:53
 */
@Aspect
@Component
public class AdminUserOptionLogAspect {

    @Autowired
    SysLogService sysLogService;

    /**
     * 定义切面表达式
     */
    @Pointcut("@annotation(cn.ch3nnn.adminblog.common.annotation.Log)")
    public void log() {
    }

    @Around("log()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 获取方法参数
        final Object[] args = point.getArgs();
        // 执行方法
        Object result = point.proceed(args);
        //异步保存日志
        saveLog(point);
        return result;

    }

    public void saveLog(ProceedingJoinPoint point) {

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Log sysLogMethod = method.getAnnotation(Log.class);

        final SysLog sysLog = new SysLog();

        if (sysLogMethod != null) {

            // 注解上的描述
            String value = sysLogMethod.value();
            // 获取请求
            HttpServletRequest request = ((ServletRequestAttributes)
                    Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            // 获取登录用户
            UserUser userUser = (UserUser) request.getSession().getAttribute("user");
            // 设置请求ip
            sysLog.setIp(IPUtils.getIpAddr(request));
            // 请求的方法名
            String className = point.getTarget().getClass().getName();
            String methodName = signature.getName();
            sysLog.setMethod(className + "." + methodName + "()");
            // 注解上的描述
            sysLog.setOperation(value);
            // 请求的参数
            Object[] args = point.getArgs();
            try {
                String params = JSON.toJSONString(args[0]);
                sysLog.setParams(params);
            } catch (Exception ignored) {
            }
            sysLog.setUsername(userUser.getUsername());
            sysLog.setCreateDate(new Date());

            sysLogService.save(sysLog);


        }
    }


}
