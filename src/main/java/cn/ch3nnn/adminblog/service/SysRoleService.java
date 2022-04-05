package cn.ch3nnn.adminblog.service;

import cn.ch3nnn.adminblog.dto.UpdateRoleMenuParam;
import cn.ch3nnn.adminblog.entity.Menu;
import cn.ch3nnn.adminblog.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public interface SysRoleService extends IService<SysRole> {

    /**
     * 更新角色数据
     *
     * @param sysRole 角色对象
     * @return
     */
    boolean updateBySysRole(SysRole sysRole);

    /**
     * 根据角色id获取拥有菜单数据
     *
     * @param roleId 角色id
     * @return
     */
    List<Menu> listMenuByRileId(Integer roleId);

    /**
     * 新增角色菜单权限中间表
     *
     * @param roleMenuParam 入参参数对象
     * @return
     */
    boolean saveRoleMenu(UpdateRoleMenuParam roleMenuParam);

    /**
     * 根据角色id删除角色菜单中间表
     *
     * @param roleId 角色id
     * @return
     */
    boolean removeRoleMenuByRoleId(Integer roleId);
}
