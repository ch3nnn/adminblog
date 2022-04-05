package cn.ch3nnn.adminblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务表
 *
 * @TableName tb_scheduled_cron
 */
@TableName(value = "tb_scheduled_cron")
@Data
public class ScheduledCron implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 定时任务完整类名
     */
    private String cronClass;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 任务描述
     */
    private String taskExplain;
    /**
     * 运行状态,1:正常;2:异常
     */
    private Byte status;

    /**
     * 状态,1:正常;2:停用
     */
    private Byte code;

    /**
     * 异常信息
     */
    private String errorMsg;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 上次运行日期
     */
    private Date lastTime;
}