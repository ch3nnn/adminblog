package cn.ch3nnn.adminblog.controller;

import cn.ch3nnn.adminblog.common.ResultCode;
import cn.ch3nnn.adminblog.dto.UpdatePwdParam;
import cn.ch3nnn.adminblog.dto.UserUserDto;
import cn.ch3nnn.adminblog.entity.UserUser;
import cn.ch3nnn.adminblog.service.SysOssService;
import cn.ch3nnn.adminblog.service.UserUserService;
import cn.ch3nnn.adminblog.utils.DjangoPasswordUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.util.HashMap;

/**
 * 管理员信息
 *
 * @Author ChenTong
 * @Date 2021/10/8 16:37
 */
@Api("管理员信息")
@Controller
@RequestMapping("/admin/profile")
public class AdminProfileController {

    @Autowired
    UserUserService userUserService;

    @Autowired
    SysOssService sysOssService;

    @RequestMapping(value = "info", method = RequestMethod.GET)
    public String info(Model model, HttpSession session) {
        UserUser user = (UserUser) session.getAttribute("user");
        final UserUser userUser = userUserService.getById(user.getId());
        model.addAttribute("user", userUser);
        return "admin/profile";
    }

    @ApiOperation("修改个人信息")
    @ResponseBody
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResultCode save(@RequestBody UserUserDto userUserDto, HttpSession session) {
        UserUser user = (UserUser) session.getAttribute("user");
        userUserDto.setId(user.getId());
        if (userUserService.updateByUserUser(userUserDto)) {
            return ResultCode.success("修改成功, 页面即将刷新");
        }
        return ResultCode.error();
    }

    @ApiOperation("管理员头像上传")
    @ResponseBody
    @RequestMapping(value = "uploadImg", method = RequestMethod.POST)
    public ResultCode uploadImg(@RequestParam(value = "img") MultipartFile file) {
        try {
            FileInputStream inputStream = (FileInputStream) file.getInputStream();
            String path = sysOssService.uploadQnFile(inputStream);
            final HashMap<String, Object> stringObjectHashMap = new HashMap<>(10);
            stringObjectHashMap.put("src", path);
            return ResultCode.success(stringObjectHashMap);
        } catch (Exception e) {
            return ResultCode.error(e.getMessage());
        }

    }


    @ApiOperation("修改密码西详情页")
    @RequestMapping(value = "updatePassword", method = RequestMethod.GET)
    public String updatePassword() {
        return "admin/updatePassword";
    }


    @ApiOperation("修改密码")
    @ResponseBody
    @RequestMapping(value = "updatePassword", method = RequestMethod.PUT)
    public ResultCode updatePassword(@RequestBody UpdatePwdParam updatePwdParam, HttpSession session) {

        final UserUser user = (UserUser) session.getAttribute("user");
        final DjangoPasswordUtils djangoPasswordUtils = new DjangoPasswordUtils();
        final UserUser userUser = userUserService.getById(user.getId());
        // 判断两次密码是否相同
        if (!djangoPasswordUtils.checkPassword(updatePwdParam.getOldPassword(), userUser.getPassword())) {
            return ResultCode.error("密码错误, 请重新输入");
        }
        if (updatePwdParam.getNewPassword().equals(updatePwdParam.getNewPassword1())) {
            final String encodePassword = djangoPasswordUtils.encode(updatePwdParam.getNewPassword(), DjangoPasswordUtils.SALT);
            userUserService.updatePasswordByUserId(encodePassword, user.getId());
            return ResultCode.success();
        }
        return ResultCode.error("两次新密码输入不一致, 请重新输入");
    }


}
