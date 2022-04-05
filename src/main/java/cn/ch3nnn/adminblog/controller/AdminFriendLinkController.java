package cn.ch3nnn.adminblog.controller;

import cn.ch3nnn.adminblog.common.ResultCode;
import cn.ch3nnn.adminblog.common.annotation.Log;
import cn.ch3nnn.adminblog.entity.FriendLink;
import cn.ch3nnn.adminblog.service.FriendLinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author chentong
 */
@Api("友链")
@Controller
@RequestMapping("admin/friend_link")
public class AdminFriendLinkController {

    @Autowired
    FriendLinkService friendLinkService;

    @Log("查看友链数据")
    @ApiOperation("友链列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {

        final List<FriendLink> friendLinks = friendLinkService.list();
        model.addAttribute("friendLinks", friendLinks);

        return "admin/friendLink";
    }

    @Log("新增友链")
    @ApiOperation("新增友链")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResultCode add(@RequestBody FriendLink friendLinks) {
        final boolean save = friendLinkService.save(friendLinks);
        if (save) {
            return ResultCode.success("添加成功");
        }
        return ResultCode.error("添加数据失败");
    }

    @Log("更新友链")
    @ResponseBody
    @ApiOperation("更新友链")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResultCode update(@RequestBody FriendLink friendLinks) {
        final boolean update = friendLinkService.updateByFriendLink(friendLinks);
        if (update) {
            return ResultCode.success("修改成功");
        }
        return ResultCode.error("修改失败");
    }

    @Log("删除友链")
    @ResponseBody
    @ApiOperation("删除友链")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResultCode delete(@ApiParam("友链id") @PathVariable int id) {

        if (friendLinkService.removeById(id)) {
            return ResultCode.success();
        }
        return ResultCode.error();
    }


}
