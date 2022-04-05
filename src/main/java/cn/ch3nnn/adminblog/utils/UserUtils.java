package cn.ch3nnn.adminblog.utils;

import cn.ch3nnn.adminblog.entity.SecurityUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author ChenTong
 * @Date 2021/9/29 14:32
 */

public class UserUtils {


    /**
     * 获取当前登录者的信息
     *
     * @return 当前者信息
     */
    public static SecurityUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }
            if (authentication instanceof UsernamePasswordAuthenticationToken) {
                return (SecurityUser) authentication.getPrincipal();
            }
        }

        return null;
    }


    /**
     * 判断此用户中是否包含roleName菜单权限
     *
     * @param roleName 角色名称
     * @return
     */
    public static Boolean hasRole(String roleName) {
        // 获取UserDetails类，
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> roleNames = new ArrayList<>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            // getAuthority()返回用户对应的菜单权限
            roleNames.add(authority.getAuthority());
        }
        return roleNames.contains(roleName);
    }

}


