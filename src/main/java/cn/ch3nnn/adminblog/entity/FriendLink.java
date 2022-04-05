package cn.ch3nnn.adminblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chentong
 * @TableName tb_friend_link
 */
@TableName(value = "tb_friend_link")
@Data
public class FriendLink implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 站点域名
     */
    private String siteDomain;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}