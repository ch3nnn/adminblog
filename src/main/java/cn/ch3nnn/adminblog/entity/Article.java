package cn.ch3nnn.adminblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author chentong
 * @TableName tb_content
 */
@TableName(value = "tb_content")
@Data
public class Article implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String body;

    /**
     * 文章摘要
     */
    private String excerpt;

    /**
     * 创建日期
     */
    private Date createdTime;

    /**
     * 发布日期
     */
    private Date modifiedTime;

    /**
     * 浏览量
     */
    private Integer views;

    /**
     * 作者id
     */
    private Integer authorId;

    /**
     * 分类id
     */
    private Integer categoryId;

    /**
     * 置顶
     */
    private Boolean top;

    /**
     * 是否展示
     */
    private Boolean isPrivate;

    /**
     * 逻辑删除
     */
    private Boolean isDelete;

    /**
     * 实体用户对象
     */
    private UserUser author;

    /**
     * 实体分类对象
     */
    private Category category;

    /**
     * 标签列表
     */
    private List<Tag> tags;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}