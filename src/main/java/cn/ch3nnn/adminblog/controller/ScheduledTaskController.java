package cn.ch3nnn.adminblog.controller;

import cn.ch3nnn.adminblog.common.ResultCode;
import cn.ch3nnn.adminblog.common.annotation.Log;
import cn.ch3nnn.adminblog.entity.ScheduledCron;
import cn.ch3nnn.adminblog.service.ScheduledCronService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 定时任务管理
 *
 * @Author ChenTong
 * @Date 2021/9/28 13:13
 */
@Controller
@RequestMapping("admin/scheduled/task")
public class ScheduledTaskController {

    @Autowired
    ScheduledCronService scheduledCronService;

    @Log("查看定时任务")
    @ApiOperation("查看定时任务")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String taskList(Model model) {
        final List<ScheduledCron> scheduledCronList = scheduledCronService.list();
        model.addAttribute("scheduledCronList", scheduledCronList);

        return "admin/scheduled";
    }

    @Log("删除定时任务")
    @ApiOperation("删除定时任务")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public ResultCode delete(@ApiParam("菜单id") @PathVariable int id) {

        if (scheduledCronService.removeById(id)) {
            return ResultCode.success();
        }
        return ResultCode.error();
    }


    @Log("更新定时任务")
    @ApiOperation("更新定时任务")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public ResultCode update(@RequestBody ScheduledCron scheduledCron) {
        final boolean update = scheduledCronService.updateByScheduledCron(scheduledCron);
        if (update) {
            return ResultCode.success("修改成功");
        }
        return ResultCode.error("修改失败");
    }


}
