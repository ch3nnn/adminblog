package cn.ch3nnn.adminblog.controller;

import cn.ch3nnn.adminblog.common.ResultCode;
import cn.ch3nnn.adminblog.common.annotation.Log;
import cn.ch3nnn.adminblog.entity.Comments;
import cn.ch3nnn.adminblog.service.CommentsService;
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
@Api("评论")
@Controller
@RequestMapping("admin/comment")
public class AdminCommentController {

    @Autowired
    CommentsService commentsService;

    @Log("查看评论列表")
    @ApiOperation("评论列表")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {

        final List<Comments> comments = commentsService.selectAll();
        model.addAttribute("comments", comments);

        return "admin/comment";
    }


    @Log("新增评论")
    @ApiOperation("新增评论")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultCode add(@RequestBody Comments comments) {
        final boolean save = commentsService.save(comments);
        if (save) {
            return ResultCode.success("添加成功");
        }
        return ResultCode.error("添加数据失败");
    }

    @Log("更新评论")
    @ApiOperation("更新评论")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResultCode update(@RequestBody Comments comments) {
        final boolean update = commentsService.updateByComments(comments);
        if (update) {
            return ResultCode.success("修改成功");
        }
        return ResultCode.error("修改失败");
    }

    @Log("删除评论")
    @ApiOperation("删除评论")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResultCode delete(@ApiParam("评论id") @PathVariable int id) {

        if (commentsService.removeById(id)) {
            return ResultCode.success();
        }
        return ResultCode.error();
    }


}
