package cn.ch3nnn.adminblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Chentong
 * @TableName user_user
 */

@TableName(value = "user_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUser implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
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
     * 是否活跃
     */
    private Boolean isActive;

    /**
     * 加入日期
     */
    private Date dateJoined;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 个人介绍
     */
    private String text;

    /**
     * 友链
     */
    private String link;

    /**
     * 头像url
     */
    private String avatarUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}