package cn.ch3nnn.adminblog.service.impl;

import cn.ch3nnn.adminblog.entity.Menu;
import cn.ch3nnn.adminblog.entity.SecurityUser;
import cn.ch3nnn.adminblog.mapper.MenuMapper;
import cn.ch3nnn.adminblog.service.MenuService;
import cn.ch3nnn.adminblog.utils.UserUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * @author chentong
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
        implements MenuService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MenuMapper menuMapper;

    /**
     * 获取子节点
     *
     * @param id      父节点id
     * @param allMenu 所有菜单列表
     * @return 每个根节点下，所有子菜单列表
     */
    public List<Menu> getChild(Integer id, List<Menu> allMenu) {
        //子菜单
        List<Menu> childList = new ArrayList<>();
        for (Menu nav : allMenu) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if (nav.getParentId().equals(id)) {
                childList.add(nav);
            }
        }
        // 递归
        for (Menu nav : childList) {
            nav.setChildren(getChild(nav.getId(), allMenu));
        }
        // 排序
        childList.sort(order());
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }

    /**
     * 排序,根据order排序
     */
    public Comparator<Menu> order() {
        return (o1, o2) -> {
            if (!o1.getSort().equals(o2.getSort())) {
                return o1.getSort() - o2.getSort();
            }
            return 0;
        };
    }

    @Override
    public List<Menu> listWithTree(boolean isUserPermissionTree) {
        try {
            List<Menu> allMenu;
            // 查询用户拥有所有菜单权限
            final SecurityUser loginUser = UserUtils.getLoginUser();
            if (isUserPermissionTree && loginUser != null) {
                allMenu = menuMapper.listByUserId(loginUser.getUserId());
            } else {
                // 查询所有菜单
                allMenu = menuMapper.list();
            }
            // 根节点
            List<Menu> rootMenu = new ArrayList<>();
            for (Menu nav : allMenu) {
                // 父节点是0的，为根节点
                if (nav.getParentId().equals(0)) {
                    rootMenu.add(nav);
                }
            }
            /* 根据Menu类的order排序 */
            rootMenu.sort(order());
            //为根菜单设置子菜单，getChild是递归调用的
            for (Menu nav : rootMenu) {
                /* 获取根节点下的所有子节点 使用getChild方法*/
                List<Menu> childList = getChild(nav.getId(), allMenu);
                // 给根节点设置子节点
                nav.setChildren(childList);
            }
            return rootMenu;
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return new ArrayList<>(0);
        }
    }

    @Override
    public boolean updateByMenu(Menu menu) {
        return retBool(menuMapper.updateByMenu(menu));
    }

}





