package cn.ch3nnn.adminblog.advice;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * controller_response_is_view 这个表示controller的返回响应类型是页面
 *
 * @Author ChenTong
 * @Date 2021/10/11 17:25
 */
@Component
public class IsViewOrJsonInterceptor implements HandlerInterceptor {

    /**
     * 验证controller返回响应类型
     * preHandle方法在控制器的处理请求方法调用之前执行, 其返回值表示是否中断后续操作，
     * 返回 true 表示继续向下执行，返回 false 表示中断后续操作。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理器
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();

        boolean isPage = method.getReturnType().equals(String.class);
        boolean isJson = method.isAnnotationPresent(ResponseBody.class);
        boolean isController = (!hm.getBeanType().isAnnotationPresent(RestController.class) && hm.getBeanType().isAnnotationPresent(Controller.class));

        // controller_response_is_view 这个表示controller的返回响应类型是页面
        request.setAttribute("method_return_is_view", isPage && !isJson && isController);

        return true;
    }

    /**
     * postHandle( )：该方法在控制器的处理请求方法调用之后、解析视图之前执行
     * 可以通过此方法对请求域中的模型和视图做进一步的修改。
     *
     * @param request      请求对象
     * @param response     响应对象
     * @param handler      处理器
     * @param modelAndView 模型和视图
     * @throws Exception
     */
    @Override
    public void postHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * afterCompletion( )：该方法在控制器的处理请求方法执行完成后执行，即视图渲染结束后执行
     * 可以通过此方法实现一些资源清理、记录日志信息等工作。
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理器
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
