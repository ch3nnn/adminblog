package cn.ch3nnn.adminblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单表
 *
 * @author chentong
 * @TableName tb_menu
 */
@TableName(value = "tb_menu")
@Data
public class Menu implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 路由
     */
    private String url;

    /**
     * 父类菜单id
     */
    private Integer parentId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 子菜单
     */
    private List<Menu> children;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}