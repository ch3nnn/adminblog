package cn.ch3nnn.adminblog.config;


import cn.ch3nnn.adminblog.security.AdminAuthenticationProvider;
import cn.ch3nnn.adminblog.security.LoginFilter;
import cn.ch3nnn.adminblog.security.SignInFailureHandler;
import cn.ch3nnn.adminblog.security.SignInSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;


/**
 * Spring Security 安全框架
 *
 * @Author ChenTong
 * @Date 2021/7/27 13:45
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义认证
     */
    @Resource
    private AdminAuthenticationProvider adminAuthenticationProvider;

    @Autowired
    SignInFailureHandler signInFailureHandler;

    @Autowired
    SignInSuccessHandler signInSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // X-Content-Type-Options 头设置允许加载静态资源文件
        http.headers()
                .contentTypeOptions().disable()
                .frameOptions().disable();

        http.csrf().disable().authorizeRequests().antMatchers("/static/*", "/css/*", "/js/*").permitAll();

        // 开启登录配置
        http.authorizeRequests()
                // 登录页面不需要权限
                .antMatchers("/admin/login").permitAll()
                .antMatchers("/admin/**").authenticated()
                .anyRequest().permitAll();  // 其他页面需要登录用户才能访问


        // 定义登录页面，未登录时，访问一个需要登录之后才能访问的接口，会自动跳转到该页面
        http.formLogin()
                .loginPage("/admin/login");

        http.logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login");

        http.rememberMe()
                .rememberMeParameter("remember");

        // 关闭csrf跨域攻击防御
        http.csrf().disable();

        // 启用rememberMe功能，将用户信息保存在cookie中
        http.rememberMe();

        // 自定义Json登录
        http.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 这里要设置自定义Django密码认证
        auth.authenticationProvider(adminAuthenticationProvider);
    }

    @Bean
    LoginFilter loginFilter() throws Exception {
        LoginFilter filter = new LoginFilter();

        filter.setAuthenticationFailureHandler(signInFailureHandler);
        filter.setAuthenticationSuccessHandler(signInSuccessHandler);

        filter.setFilterProcessesUrl("/admin/login2");
        // 这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());

        return filter;
    }

}