package cn.ch3nnn.adminblog.controller;

import cn.ch3nnn.adminblog.entity.Menu;
import cn.ch3nnn.adminblog.entity.SysLog;
import cn.ch3nnn.adminblog.service.ArticleService;
import cn.ch3nnn.adminblog.service.MenuService;
import cn.ch3nnn.adminblog.service.SysLogService;
import cn.ch3nnn.adminblog.service.UserUserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 用户登录控制器
 *
 * @Author ChenTong
 * @Date 2021/7/22 22:51
 */
@Controller
@RequestMapping("/admin")
public class AdminIndexController {

    @Autowired
    UserUserService userUserService;

    @Autowired
    ArticleService articleService;

    @Autowired
    MenuService menuService;

    @Autowired
    SysLogService sysLogService;

    @GetMapping("/index")
    public String index(Model model,
                        HttpSession session,
                        @RequestParam(defaultValue = "1") int pageNum,
                        @RequestParam(defaultValue = "5") int pageSize) {

        final Map<String, Object> indexMap = articleService.index();
        model.addAttribute("blogInfoMap", indexMap);

        // 日志信息分页
        final Page<SysLog> page = new Page<>(pageNum, pageSize);
        Page<SysLog> logListPage = sysLogService.findListPage(page);
        model.addAttribute("pageInfo", logListPage);
        // 菜单树
        final List<Menu> menus = menuService.listWithTree(true);
        session.setAttribute("menus", menus);
        return "admin/index";

    }

    /**
     * 登录校验
     *
     * @return 登录成功跳转登录成功页面，登录失败返回登录页面
     */
    @GetMapping(value = "/login")
    public String login() {
        return "admin/login";
    }

}
