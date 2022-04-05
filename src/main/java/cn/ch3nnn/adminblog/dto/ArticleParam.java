package cn.ch3nnn.adminblog.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chentong
 * @TableName tb_content
 */
@Data
@ApiModel("文章修改信息")
public class ArticleParam implements Serializable {
    /**
     *
     */
    @ApiModelProperty("文章id")
    private Integer id;

    /**
     * 文章标题
     */
    @ApiModelProperty("文章标题")
    @Length(min = 1, max = 24, message = "文章标题长度必须位于1到24之间")
    private String title;

    /**
     * 文章内容
     */
    @ApiModelProperty("文章内容")
    @NotBlank(message = "名字为必填项")
    private String body;

    /**
     * 创建日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty("创建日期")
    private Date createdTime;

    /**
     * 发布日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty("发布日期")
    private Date modifiedTime;

    /**
     * 浏览量
     */
    @ApiModelProperty("浏览量")
    private Integer views;

    /**
     * 作者id
     */
    @ApiModelProperty("用户id")
    private Integer authorId;

    /**
     * 分类id
     */
    @ApiModelProperty("分类id")
    private Integer categoryId;

    /**
     * 置顶
     */
    @ApiModelProperty("是否置顶")
    private Boolean top;

    /**
     * 是否展示
     */
    @ApiModelProperty("是否展示")
    private Boolean isPrivate;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}