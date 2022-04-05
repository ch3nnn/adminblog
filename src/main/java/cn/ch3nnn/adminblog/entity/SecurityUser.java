package cn.ch3nnn.adminblog.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 自定义用于Spring Security安全框架-用户详情
 *
 * @Author ChenTong
 * @Date 2021/7/27 15:40
 */
@Data
@Slf4j
public class SecurityUser implements UserDetails {
    /**
     * 当前登录用户
     */
    private transient UserUser currentUserInfo;

    public SecurityUser() {
    }

    public SecurityUser(UserUser user) {
        if (user != null) {
            this.currentUserInfo = user;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("admin");
        authorities.add(authority);
        return authorities;
    }


    public int getUserId() {
        return currentUserInfo.getId();
    }

    /**
     * 根据django user用户表字段 判断是否admin权限
     */
    public boolean getIsSuperuser() {
        return currentUserInfo.getIsSuperuser();
    }

    @Override
    public String getPassword() {
        return currentUserInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return this.currentUserInfo != null ? this.currentUserInfo.getUsername() : "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}