package cn.ch3nnn.adminblog.controller;

import cn.ch3nnn.adminblog.common.ResultCode;
import cn.ch3nnn.adminblog.common.annotation.Log;
import cn.ch3nnn.adminblog.entity.Menu;
import cn.ch3nnn.adminblog.service.MenuService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * 菜单信息
 *
 * @Author ChenTong
 * @Date 2021/7/29 16:35
 */
@Controller
@RequestMapping("/admin/menu")
public class AdminMenuController {

    @Autowired
    MenuService menuService;

    @Log("查看菜单数据")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        final List<Menu> menus = menuService.listWithTree(false);
        model.addAttribute("menus", menus);
        return "admin/menu";
    }

    @ResponseBody
    @RequestMapping(value = "api/list", method = RequestMethod.GET)
    public ResultCode list() {
        final List<Menu> menus = menuService.listWithTree(false);
        final HashMap<String, Object> resHashMap = new HashMap<>(10);
        resHashMap.put("menus", menus);
        return ResultCode.success(resHashMap);
    }


    @Log("添加菜单数据")
    @ApiOperation("添加菜单数据")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResultCode add(@RequestBody Menu menu) {
        final boolean save = menuService.save(menu);
        if (save) {
            return ResultCode.success("添加成功");
        }
        return ResultCode.error("添加菜单数据失败");


    }

    @Log("更新菜单数据")
    @ApiOperation("更新菜单数据")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResultCode update(@RequestBody Menu menu) {
        final boolean update = menuService.updateByMenu(menu);
        if (update) {
            return ResultCode.success("修改成功");
        }
        return ResultCode.error("修改失败");
    }

    @Log("删除菜单数据")
    @ApiOperation("删除菜单数据")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResultCode delete(@ApiParam("菜单id") @PathVariable int id) {

        if (menuService.removeById(id)) {
            return ResultCode.success();
        }
        return ResultCode.error();


    }

}
