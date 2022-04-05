package cn.ch3nnn.adminblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chentong
 * @TableName tb_comments
 */
@TableName(value = "tb_comments")
@Data
public class Comments implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 评论内容
     */
    private String text;

    /**
     * 评论时间
     */
    private Date createdTime;

    /**
     * 文章id
     */
    private Integer newId;

    /**
     * 评论用户id
     */
    private Integer userId;

    /**
     * 父评论id
     */
    private Integer parentId;


    /**
     * 用户对象
     */
    private UserUser user;

    /**
     * 文章对象
     */
    private Article article;

    /**
     * 父评论对象
     */
    private Comments comments;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}