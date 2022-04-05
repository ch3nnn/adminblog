package cn.ch3nnn.adminblog.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回前端热力图结构数据
 *
 * @Author ChenTong
 * @Date 2021/10/15 09:27
 */
@NoArgsConstructor
@Data
public class HeatMapDataDto {

    @JSONField(name = "lng")
    private Double lng;

    @JSONField(name = "lat")
    private Double lat;

    @JSONField(name = "count")
    private Integer count;
}
