package cn.ch3nnn.adminblog.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 日志切面处理
 *
 * @Author ChenTong
 * @Date 2021/7/22 22:41
 */

@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * 定义切面表达式
     */
    @Pointcut("execution(public * cn.ch3nnn.adminblog.controller..*(..))")
    public void webLog() {
    }

    /**
     * 在切面之前执行
     * 请求url
     * 访问者ip
     * 调用方法 classMethod
     * 参数args
     * 返回内容
     */
    @Before("webLog()")
    public void before(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            // 记录下请求内容
            log.info("URL : " + request.getRequestURL().toString());
            log.info("HTTP_METHOD : " + request.getMethod());
            log.info("IP : " + request.getRemoteAddr());
            log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        }

    }

    /**
     * 在切面之后执行
     *
     * @param result
     */
    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void afterReturning(Object result) {
        log.info("RESPONSE : " + result);
    }
}
