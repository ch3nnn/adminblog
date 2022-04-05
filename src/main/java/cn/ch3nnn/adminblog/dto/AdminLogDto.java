package cn.ch3nnn.adminblog.dto;

import cn.ch3nnn.adminblog.entity.UserUser;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chentong
 */
@Data
public class AdminLogDto implements Serializable {


    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    private String actionTime;

    /**
     *
     */
    private String objectId;

    /**
     *
     */
    private String objectRepr;

    /**
     *
     */
    private Short actionFlag;

    /**
     *
     */
    private JSONArray changeMessage;

    /**
     *
     */
    private Integer contentTypeId;

    /**
     *
     */
    private UserUser user;

}