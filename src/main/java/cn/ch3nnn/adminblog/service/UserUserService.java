package cn.ch3nnn.adminblog.service;

import cn.ch3nnn.adminblog.dto.UserUserDto;
import cn.ch3nnn.adminblog.entity.SecurityUser;
import cn.ch3nnn.adminblog.entity.SysRole;
import cn.ch3nnn.adminblog.entity.UserUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author chentong
 */
public interface UserUserService extends IService<UserUser> {

    /**
     * 查询用户
     *
     * @return
     */
    @Override
    List<UserUser> list();


    /**
     * 根据 UserUserDto 查询所有
     *
     * @return
     */
    List<UserUserDto> listAll();

    /**
     * 根据用户名查询用户对象
     *
     * @param username 用户名
     * @return
     */
    UserUser getByUsername(String username);


    /**
     * 更新用户数据
     *
     * @param userUserDto 用户对象
     * @return
     */
    boolean updateByUserUser(UserUserDto userUserDto);


    /**
     * 根据账号获取用户信息
     *
     * @param username: 用户名
     * @return: org.springframework.security.core.userdetails.UserDetails
     */
    SecurityUser getSecurityByUsername(String username);

    /**
     * 根据用户 id 修改密码
     *
     * @param password 密码
     * @param userId   用户id
     * @return
     */
    boolean updatePasswordByUserId(String password, Integer userId);


    /**
     * 根据用户id获取对应权限
     *
     * @param userId 用户id
     * @return
     */
    List<SysRole> getSysRoleByUserId(Integer userId);


}
