package cn.ch3nnn.adminblog.service;

import cn.ch3nnn.adminblog.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * @author chentong
 */
public interface MenuService extends IService<Menu> {


    /**
     * 查询菜单树
     *
     * @param isUserPermissionTree 是否查看该登录用户下所有菜单数据
     * @return
     */
    List<Menu> listWithTree(boolean isUserPermissionTree);

    /**
     * 更新菜单数据
     *
     * @param menu 修改菜单数据对象
     * @return
     */
    boolean updateByMenu(Menu menu);

}
