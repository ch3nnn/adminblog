package cn.ch3nnn.adminblog.security;

import cn.ch3nnn.adminblog.common.ResultCode;
import cn.ch3nnn.adminblog.dto.UserUserDto;
import cn.ch3nnn.adminblog.entity.SecurityUser;
import cn.ch3nnn.adminblog.entity.UserUser;
import cn.ch3nnn.adminblog.service.UserUserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 * 登录成功处理程序
 *
 * @Author ChenTong
 * @Date 2021/10/17 02:16
 */
@Component
public class SignInSuccessHandler implements AuthenticationSuccessHandler {


    @Autowired
    UserUserService userUserService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication auth
    ) throws IOException, ServletException {

        final SecurityUser user = (SecurityUser) auth.getPrincipal();
        final UserUser userUser = user.getCurrentUserInfo();
        request.getSession().setAttribute("user", userUser);

        // 记录登录时间
        final UserUserDto userUserDto = new UserUserDto();
        userUserDto.setLastLogin(new Date());
        userUserDto.setId(userUser.getId());
        userUserService.updateByUserUser(userUserDto);

        ResultCode success = ResultCode.success();
        success.setMsg("登录成功");
        final HashMap<String, Object> dataMap = new HashMap<>(10);
        dataMap.put("url", "/admin/index");
        success.setData(dataMap);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(JSON.toJSONString(success));
    }
}