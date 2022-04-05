package cn.ch3nnn.adminblog.controller;


import cn.ch3nnn.adminblog.common.ResultCode;
import cn.ch3nnn.adminblog.entity.Category;
import cn.ch3nnn.adminblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ChenTong
 */
@Controller
@RequestMapping("admin/category")
public class AdminCategoryController {

    @Autowired
    CategoryService categoryService;

    /**
     * 新增分类
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultCode add(@RequestBody Category category) {
        boolean save = categoryService.save(category);
        if (save) {
            return ResultCode.success();
        }
        return ResultCode.error();


    }


}
