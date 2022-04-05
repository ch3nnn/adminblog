package cn.ch3nnn.adminblog.mapper;

import cn.ch3nnn.adminblog.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author chentong
 * @Entity cn.ch3nnn.adminblog.entity.Menu
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {


    /**
     * 修改菜单
     *
     * @param menu 修改菜单数据对象
     * @return
     */
    int updateByMenu(Menu menu);


    /**
     * 获取用户拥有菜单权限
     *
     * @param userId 用户id
     * @return
     */
    List<Menu> listByUserId(int userId);

    /**
     * 获取所有菜单数据
     *
     * @return
     */
    List<Menu> list();
}




