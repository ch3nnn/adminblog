package cn.ch3nnn.adminblog.controller;


import cn.ch3nnn.adminblog.common.ResultCode;
import cn.ch3nnn.adminblog.entity.Tag;
import cn.ch3nnn.adminblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ChenTong
 */
@Controller
@RequestMapping("admin/tag")
public class AdminTagController {

    @Autowired
    TagService tagService;

    /**
     * 新增分类
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultCode add(@RequestBody Tag tag) {
        boolean save = tagService.save(tag);
        if (save) {
            return ResultCode.success();
        }
        return ResultCode.error();


    }


}
