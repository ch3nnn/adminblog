package cn.ch3nnn.adminblog.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取es nginx日志中geo请求用户ip维度坐标
 *
 * @Author ChenTong
 * @Date 2021/10/14 16:23
 */
@NoArgsConstructor
@Data
public class EsNginxLogGeoDto {

    @JSONField(name = "source")
    private SourceDTO source;

    @NoArgsConstructor
    @Data
    public static class SourceDTO {
        @JSONField(name = "geo")
        private GeoDTO geo;

        @NoArgsConstructor
        @Data
        public static class GeoDTO {
            @JSONField(name = "location")
            private LocationDTO location;

            @NoArgsConstructor
            @Data
            public static class LocationDTO {
                @JSONField(name = "lon")
                private Double lon;
                @JSONField(name = "lat")
                private Double lat;
            }
        }
    }
}
