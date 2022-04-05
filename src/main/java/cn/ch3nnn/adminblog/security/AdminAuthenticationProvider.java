package cn.ch3nnn.adminblog.security;

import cn.ch3nnn.adminblog.entity.SecurityUser;
import cn.ch3nnn.adminblog.entity.SysRole;
import cn.ch3nnn.adminblog.entity.UserUser;
import cn.ch3nnn.adminblog.service.SysUserRoleService;
import cn.ch3nnn.adminblog.service.UserUserService;
import cn.ch3nnn.adminblog.utils.DjangoPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义身份验证提供程序-验证Django用户密码
 *
 * @Author ChenTong
 * @Date 2021/7/27 15:37
 */

@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserUserService userUserService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取前端表单中输入后返回的用户名、密码
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        final SecurityUser userInfo = userUserService.getSecurityByUsername(userName);
        final UserUser currentUserInfo = userInfo.getCurrentUserInfo();
        if (currentUserInfo == null) {
            throw new UsernameNotFoundException("用户名不存在！ 请重新登录。");
        }

        if (!new DjangoPasswordUtils().checkPassword(password, userInfo.getPassword())) {
            throw new BadCredentialsException("密码错误！ 请重新登录。");
        }
        // 验证Django生成用户是否admin 是否有用户角色
        final List<SysRole> byUserId = sysUserRoleService.getByUserId(userInfo.getUserId());
        if (byUserId.size() == 0 && !userInfo.getIsSuperuser()) {
            throw new BadCredentialsException("用户无权限！ 请授权登录。");
        }

        // 创建List集合，用来保存用户菜单权限，GrantedAuthority对象代表赋予当前用户的权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 获得当前用户角色集合
        final List<SysRole> roles = userUserService.getSysRoleByUserId(currentUserInfo.getId());
        for (SysRole role : roles) {
            //将关联对象role的name属性保存为用户的认证权限
            authorities.add(new SimpleGrantedAuthority(role.getRoleCode()));
        }

        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 确保authentication能转成该类
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}