package cn.ch3nnn.adminblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 文章标签中间表
 *
 * @author chentong
 * @TableName tb_content_tag
 */
@TableName(value = "tb_content_tag")
@Data
public class ArticleTag implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文章id
     */
    private Integer postId;

    /**
     * 标签id
     */
    private Integer tagId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}