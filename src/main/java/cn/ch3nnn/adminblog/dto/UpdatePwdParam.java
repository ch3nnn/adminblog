package cn.ch3nnn.adminblog.dto;

import lombok.Data;

/**
 * @Author ChenTong
 * @Date 2021/10/9 16:57
 */
@Data
public class UpdatePwdParam {

    /**
     * 旧密码
     */
    String oldPassword;

    /**
     * 新密码
     */
    String newPassword;

    /**
     * 确认新密码
     */
    String newPassword1;
}
