package cn.ch3nnn.adminblog.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.ConnectException;


/**
 * 拦截异常处理
 *
 * @Author ChenTong
 * @Date 2021/7/22 22:09
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    String msg;

    /**
     * 当发生Exception类的异常时，进入该方法
     *
     * @param exception Exception类的异常对象
     * @param request   请求对象
     * @param response  响应对象
     * @return
     * @throws Exception
     */
    @ExceptionHandler({Exception.class})
    public ModelAndView exceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!(exception instanceof RuntimeException) && !(exception instanceof ConnectException)) {
            log.error("exception error", exception);
            return null;
        }

        // 拦截器获取响应是否为Json或者modelView
        Object o = request.getAttribute("method_return_is_view");
        if (o == null) {
            throw exception;
        }

        // security Authentication 不通过
        if (exception instanceof AccessDeniedException) {
            msg = "权限不够: " + exception.getMessage();
        } else {
            msg = exception.getMessage();
        }

        boolean isView = (Boolean) o;
        // 返回页面
        if (isView) {
            ModelAndView mv = new ModelAndView();
            mv.addObject("exception", exception);
            mv.addObject("msg", msg);
            // 跳转到resource/templates/error/error.html页面
            mv.setViewName("error/error");
            return mv;
        }

        // 返回json
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        mv.addObject("code", 0);
        mv.addObject("msg", msg);
        mv.addObject("data", null);

        return mv;

    }
}
