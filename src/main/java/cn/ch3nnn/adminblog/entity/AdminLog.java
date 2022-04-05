package cn.ch3nnn.adminblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chentong
 * @TableName django_admin_log
 */
@TableName(value = "django_admin_log")
@Data
public class AdminLog implements Serializable {


    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 记录日期
     */
    private String actionTime;

    /**
     * 记录对象id
     */
    private String objectId;

    /**
     *
     */
    @NotNull
    private String objectRepr;

    /**
     *
     */
    @NotNull
    private Short actionFlag;

    /**
     * 操作信息
     */
    @NotNull
    private String changeMessage;

    /**
     *
     */
    private Integer contentTypeId;

    /**
     * 操作用户id
     */
    private int userId;


    private UserUser user;

    public void setActionTime(Date actionTime) {
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd:HH:mm:ss");
        this.actionTime = dateFormat.format(actionTime);
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}