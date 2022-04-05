package cn.ch3nnn.adminblog.service.impl;

import cn.ch3nnn.adminblog.entity.SysRole;
import cn.ch3nnn.adminblog.entity.SysUserRole;
import cn.ch3nnn.adminblog.mapper.SysUserRoleMapper;
import cn.ch3nnn.adminblog.service.SysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author chentong
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
        implements SysUserRoleService {

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysRole> getByUserId(Integer userId) {
        return sysUserRoleMapper.selectByUserId(userId);
    }
}




