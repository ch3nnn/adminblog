package cn.ch3nnn.adminblog.controller;

import cn.ch3nnn.adminblog.common.ResultCode;
import cn.ch3nnn.adminblog.common.annotation.Log;
import cn.ch3nnn.adminblog.dto.UpdateRoleMenuParam;
import cn.ch3nnn.adminblog.entity.Menu;
import cn.ch3nnn.adminblog.entity.SysRole;
import cn.ch3nnn.adminblog.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author ChenTong
 * @Date 2021/9/30 10:36
 */
@Api("角色管理")
@Controller
@RequestMapping("admin/sysRole")
public class AdminSysRoleController {

    @Autowired
    SysRoleService sysRoleService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index() {
        return "admin/sysRole";
    }


    @Log("新增角色数据")
    @ApiOperation("新增角色数据")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResultCode add(@RequestBody SysRole sysRole) {
        final boolean save = sysRoleService.save(sysRole);
        if (save) {
            return ResultCode.success();
        }
        return ResultCode.error();
    }


    @Log("查看角色数据")
    @ApiOperation("查看角色数据")
    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResultCode list() {
        List<SysRole> roles = sysRoleService.list();
        return ResultCode.success(roles);
    }

    @Log("更新角色数据")
    @ApiOperation("更新角色数据")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResultCode update(@RequestBody SysRole sysRole) {
        final boolean update = sysRoleService.updateBySysRole(sysRole);
        if (update) {
            return ResultCode.success("修改成功");
        }
        return ResultCode.error("修改失败");
    }

    @Log("更新角色菜单权限")
    @ApiOperation("更新角色菜单权限")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    @RequestMapping(value = "roleMenu", method = RequestMethod.PUT)
    public ResultCode updateRoleMenu(@RequestBody UpdateRoleMenuParam roleMenuParam) {
        sysRoleService.removeRoleMenuByRoleId(roleMenuParam.getRoleId());
        final boolean update = sysRoleService.saveRoleMenu(roleMenuParam);
        if (update) {
            return ResultCode.success("修改成功");
        }
        return ResultCode.error("修改失败");
    }


    @Log("删除角色数据")
    @ApiOperation("删除角色数据")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResultCode delete(@ApiParam("角色id") @PathVariable int id) {

        if (sysRoleService.removeById(id)) {
            return ResultCode.success();
        }
        return ResultCode.error();

    }


    @ApiOperation("查看角色拥有菜单")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "hasRoleMenu/{roleId}", method = RequestMethod.GET)
    public ResultCode hasRoleMenu(@ApiParam("角色id") @PathVariable int roleId) {
        final List<Menu> menuList = sysRoleService.listMenuByRileId(roleId);
        return ResultCode.success(menuList);
    }

}
