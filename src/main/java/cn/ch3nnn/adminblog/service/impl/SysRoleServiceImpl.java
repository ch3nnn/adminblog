package cn.ch3nnn.adminblog.service.impl;

import cn.ch3nnn.adminblog.dto.UpdateRoleMenuParam;
import cn.ch3nnn.adminblog.entity.Menu;
import cn.ch3nnn.adminblog.entity.SysRole;
import cn.ch3nnn.adminblog.mapper.SysRoleMapper;
import cn.ch3nnn.adminblog.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author chentong
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
        implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    public boolean save(SysRole entity) {
        entity.setIsEnable(true);
        entity.setIsDel(false);
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        return super.save(entity);
    }

    @Override
    public boolean updateBySysRole(SysRole sysRole) {
        sysRole.setUpdateTime(new Date());
        return retBool(sysRoleMapper.updateBySysRole(sysRole));
    }

    @Override
    public List<Menu> listMenuByRileId(Integer roleId) {
        return sysRoleMapper.selectMenuByRileId(roleId);
    }

    @Override
    public boolean saveRoleMenu(UpdateRoleMenuParam roleMenuParam) {
        final Integer integer = sysRoleMapper.insertRoleMenu(roleMenuParam);
        return retBool(integer);

    }

    @Override
    public boolean removeRoleMenuByRoleId(Integer roleId) {
        final Integer integer = sysRoleMapper.deleteRoleMenuByRoleId(roleId);
        return retBool(integer);
    }
}




