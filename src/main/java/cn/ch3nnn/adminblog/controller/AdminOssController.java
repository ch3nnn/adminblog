package cn.ch3nnn.adminblog.controller;

import cn.ch3nnn.adminblog.common.ResultCode;
import cn.ch3nnn.adminblog.common.annotation.Log;
import cn.ch3nnn.adminblog.service.SysOssService;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.util.HashMap;

/**
 * @Author ChenTong
 * @Date 2021/11/5 14:35
 */
@Controller
@RequestMapping("/admin/oss")
public class AdminOssController {

    @Autowired
    SysOssService sysOssService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index() {
        return "admin/sysOss";
    }

    @Log("查看OSS列表数据")
    @ApiOperation("查看OSS列表数据")
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultCode list() {
        final HashMap<String, Object> hashMap = new HashMap<>(2);
        hashMap.put("url", "https://dev-cdn.ch3nnn.cn/");
        hashMap.put("fileInfos", sysOssService.list(""));
        return ResultCode.success(hashMap);
    }

    @SneakyThrows
    @Log("上传OSS文件")
    @ApiOperation("上传OSS文件")
    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResultCode upload(@RequestParam(value = "img") MultipartFile file) {
        FileInputStream inputStream = (FileInputStream) file.getInputStream();
        final String path = sysOssService.uploadQnFile(inputStream);
        if (!path.isEmpty()) {
            return ResultCode.success("上传成功");
        }
        return ResultCode.error("上传失败");

    }

    @Log("删除OSS文件")
    @ApiOperation("删除OSS文件")
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResultCode delete(@RequestBody String[] keys) {
        if (sysOssService.delete(keys)) {
            return ResultCode.success("删除成功");
        }
        return ResultCode.error("删除失败");
    }

}
