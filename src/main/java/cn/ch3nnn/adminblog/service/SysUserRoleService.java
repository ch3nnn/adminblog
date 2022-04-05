package cn.ch3nnn.adminblog.service;

import cn.ch3nnn.adminblog.entity.SysRole;
import cn.ch3nnn.adminblog.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface SysUserRoleService extends IService<SysUserRole> {


    List<SysRole> getByUserId(Integer userId);
}
