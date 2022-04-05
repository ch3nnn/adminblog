package cn.ch3nnn.adminblog.config;

import cn.ch3nnn.adminblog.advice.IsViewOrJsonInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring mvc 配置项
 *
 * @Author ChenTong
 * @Date 2021/10/11 17:39
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    IsViewOrJsonInterceptor isViewOrJsonInterceptor;

    /**
     * 添加拦截器
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // controller_response_is_view 这个表示controller的返回响应类型是页面
        // 所有路径都被拦截
        registry.addInterceptor(isViewOrJsonInterceptor).addPathPatterns("/**");

    }

}
