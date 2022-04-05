package cn.ch3nnn.adminblog.mapper;

import cn.ch3nnn.adminblog.dto.UpdateRoleMenuParam;
import cn.ch3nnn.adminblog.entity.Menu;
import cn.ch3nnn.adminblog.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity cn.ch3nnn.adminblog.entity.SysRole
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 更新角色数据
     *
     * @param sysRole 角色对象
     * @return
     */
    int updateBySysRole(SysRole sysRole);

    /**
     * 根据角色id获取拥有菜单数据
     *
     * @param roleId 角色id
     * @return
     */
    List<Menu> selectMenuByRileId(@Param("roleId") Integer roleId);

    /**
     * 新增角色菜单权限中间表
     *
     * @param roleMenuParam 入参参数对象
     * @return
     */
    Integer insertRoleMenu(UpdateRoleMenuParam roleMenuParam);

    /**
     * 根据角色id删除角色菜单中间表
     *
     * @param roleId 角色id
     * @return
     */
    Integer deleteRoleMenuByRoleId(@Param("roleId") Integer roleId);
}




