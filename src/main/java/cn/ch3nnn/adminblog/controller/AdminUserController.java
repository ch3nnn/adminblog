package cn.ch3nnn.adminblog.controller;

import cn.ch3nnn.adminblog.common.ResultCode;
import cn.ch3nnn.adminblog.common.annotation.Log;
import cn.ch3nnn.adminblog.dto.UserUserDto;
import cn.ch3nnn.adminblog.entity.UserUser;
import cn.ch3nnn.adminblog.service.SysRoleService;
import cn.ch3nnn.adminblog.service.UserUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户管理
 *
 * @Author ChenTong
 * @Date 2021/9/26 14:23
 */
@Controller
@RequestMapping("admin/users")
public class AdminUserController {

    @Autowired
    UserUserService userUserService;

    @Autowired
    SysRoleService sysRoleService;

    @Log("查看用户数据")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        final List<UserUserDto> hashMaps = userUserService.listAll();
        model.addAttribute("userList", hashMaps);
        return "admin/user";
    }

    @Log("删除用户数据")
    @ApiOperation("删除用户数据")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResultCode delete(@ApiParam("用户id") @PathVariable int id) {

        if (userUserService.removeById(id)) {
            return ResultCode.success();
        }
        return ResultCode.error();


    }

    @Log("新增用户数据")
    @ApiOperation("新增用户数据")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResultCode add(@RequestBody UserUser userUser) {
        if (userUserService.save(userUser)) {
            return ResultCode.success();
        }
        return ResultCode.error();
    }


    @Log("修改用户")
    @ApiOperation("修改用户")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResultCode update(@RequestBody UserUserDto userUserDto) {
        final boolean update = userUserService.updateByUserUser(userUserDto);
        if (update) {
            return ResultCode.success("修改成功");
        }
        return ResultCode.error("修改失败");
    }


}
