package cn.ch3nnn.adminblog.dto;

import cn.ch3nnn.adminblog.entity.SysRole;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @Author ChenTong
 * @Date 2021/9/29 16:08
 */

@Data
public class UserUserDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String password;
    /**
     *
     */
    private Date lastLogin;
    /**
     *
     */
    private Boolean isSuperuser;
    /**
     *
     */
    private String username;
    /**
     *
     */
    private String firstName;
    /**
     *
     */
    private String lastName;
    /**
     *
     */
    private String email;
    /**
     *
     */
    private Boolean isStaff;
    /**
     *
     */
    private Boolean isActive;
    /**
     *
     */
    private Date dateJoined;
    /**
     *
     */
    private String nickname;
    /**
     *
     */
    private String text;
    /**
     *
     */
    private String link;
    /**
     *
     */
    private String avatarUrl;

    private String provider;

    /**
     * 角色id
     */
    private List<Integer> roleIds;

    /**
     * 角色
     */
    private List<SysRole> sysRoleList;
}