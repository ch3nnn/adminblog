package cn.ch3nnn.adminblog.controller;

import cn.ch3nnn.adminblog.common.ResultCode;
import cn.ch3nnn.adminblog.common.annotation.Log;
import cn.ch3nnn.adminblog.dto.ArticleParam;
import cn.ch3nnn.adminblog.entity.Article;
import cn.ch3nnn.adminblog.entity.Category;
import cn.ch3nnn.adminblog.entity.Tag;
import cn.ch3nnn.adminblog.entity.UserUser;
import cn.ch3nnn.adminblog.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

/**
 * 文章管理
 *
 * @Author ChenTong
 * @Date 2021/8/2 17:10
 */
@Api("文章管理")
@Controller
@RequestMapping("admin/article")
public class AdminArticleController {

    @Autowired
    @Qualifier("articleService")
    ArticleService articleService;

    @Autowired
    UserUserService userUserService;

    @Autowired
    TagService tagService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    SysLogService sysLogService;


    /**
     * 分页查询文章列表数据
     *
     * @param model    使用model带回前端
     * @param pageNum  当前页
     * @param pageSize 显示条数
     */
    @ApiOperation("分页查询文章列表数据")
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model,
                       @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        try {
            // 1.引入分页插件,pageNum是第几页，pageSize是每页显示多少条,默认查询总数count
            PageHelper.startPage(pageNum, pageSize);
            // 2.紧跟的查询就是一个分页查询-必须紧跟.后面的其他查询不会被分页，除非再次调用PageHelper.startPage
            final List<Article> articleList = articleService.list();
            PageInfo<Article> pageInfo = new PageInfo<>(articleList);
            model.addAttribute("pageInfo", pageInfo);
            return "admin/article";
        } finally {
            // 清理 ThreadLocal 存储的分页参数,保证线程安全
            PageHelper.clearPage();

        }
    }


    @ApiOperation("查看编辑文章内容")
    @RequestMapping(value = "edit/detail/{articleId}")
    public String edit(Model model, @ApiParam("文章id") @PathVariable int articleId) throws ParseException {
        final List<UserUser> userList = userUserService.list();
        final Article article = articleService.getByIdArticle(articleId);
        final List<Tag> tagList = tagService.list();
        final List<Category> categoryList = categoryService.list();
        model.addAttribute("article", article);
        model.addAttribute("userList", userList);
        model.addAttribute("tagList", tagList);
        model.addAttribute("categoryList", categoryList);

        return "admin/edit_article";
    }

    @ApiOperation("新增文章")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "add")
    public String add(Model model) {
        final List<UserUser> userList = userUserService.list();
        final List<Category> categoryList = categoryService.list();
        final List<Tag> tagList = tagService.list();

        model.addAttribute("userList", userList);
        model.addAttribute("tagList", tagList);
        model.addAttribute("categoryList", categoryList);

        return "admin/add_article";
    }


    @Log("新增文章")
    @ApiOperation("新增文章")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "add")
    public String add(ArticleParam articleParam,
                      @ApiParam("标签列表id") @RequestParam(value = "tags", required = false) List<Integer> tagsIdList) {

        articleService.save(articleParam, tagsIdList);
        return "redirect:/admin/article/list";


    }


    @ApiOperation("保存编辑文章")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "saveEdit/{articleId}")
    public String saveEdit(HttpServletRequest request, Model model,
                           ArticleParam articleParam,
                           HttpSession session,
                           @PathVariable int articleId,
                           @ApiParam("标签列表id") @RequestParam(value = "tags", required = false) List<Integer> tagsIdList) {

        UserUser user = (UserUser) session.getAttribute("user");

        final String diffArticleFields = articleService.getDiffArticleFields(articleParam, articleId);
        final boolean isSuccess = articleService.updateByArticleId(articleParam, tagsIdList);
        if (isSuccess) {
            // 记录操作日志
            sysLogService.recordAdminLogInfo(request, user, diffArticleFields);
            model.addAttribute("msg", "修改成功");
        } else {
            model.addAttribute("msg", "修改失败");
        }
        // 转发文章详情
        return "forward:/admin/article/edit/detail/" + articleId;
    }

    @Log("搜索文章数据")
    @ApiOperation("文章列表搜索标题")
    @GetMapping("searchArticle")
    public String searchArticleTitle(Model model,
                                     @ApiParam("搜索类型 标题、内容") @RequestParam(value = "search_field") String searchField,
                                     @ApiParam("搜索关键字") @RequestParam(value = "keyword") String keyword) {

        final List<Article> articleList = articleService.search(searchField, keyword);
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/article";

    }


    @Log("删除文章")
    @ApiOperation("根据文章id 逻辑删除")
    @ResponseBody
    @DeleteMapping("delete/{articleId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResultCode delete(@ApiParam("文章id") @PathVariable int articleId) {
        final boolean removeById = articleService.removeById(articleId);
        if (!removeById) {
            return ResultCode.error("删除失败");
        }
        return ResultCode.success();


    }

}
