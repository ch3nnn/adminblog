package cn.ch3nnn.adminblog.service.impl;

import cn.ch3nnn.adminblog.dto.EsNginxLogGeoDto;
import cn.ch3nnn.adminblog.dto.HeatMapDataDto;
import cn.ch3nnn.adminblog.service.SearchService;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedTopHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author ChenTong
 * @Date 2021/10/15 09:29
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Override
    public List<HeatMapDataDto> queryIpHeatMap() throws Exception {

        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 按城市分组聚合统计出维度坐标
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("name").field("source.geo.city_name")
                .size(1000)
                // 子聚合统计只显示维度字段
                .subAggregation(AggregationBuilders.topHits("location")
                        .fetchSource(new String[]{"source.geo.location"}, null)
                        .size(1));
        // 筛选出中国区城市数据
        searchSourceBuilder.query(QueryBuilders.matchQuery("source.geo.country_iso_code", "CN"))
                .aggregation(aggregation).size(0);
        // 查询filebeat索引
        searchRequest.indices("filebeat-7.6.2").source(searchSourceBuilder);
        final SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        Aggregations aggregations = searchResponse.getAggregations();
        final Terms byAggregation = aggregations.get("name");

        final List<HeatMapDataDto> heatMapDataDtoList = new ArrayList<>();
        for (Terms.Bucket bucket : byAggregation.getBuckets()) {
            // 取子聚合
            ParsedTopHits hits = bucket.getAggregations().get("location");
            for (SearchHit hit : hits.getHits()) {
                // 将json字符串转换为json对象
                final EsNginxLogGeoDto sourceDTO = JSON.parseObject(hit.getSourceAsString(), EsNginxLogGeoDto.class);
                final EsNginxLogGeoDto.SourceDTO.GeoDTO.LocationDTO location = sourceDTO.getSource().getGeo().getLocation();

                final HeatMapDataDto heatMapDataDto = new HeatMapDataDto();
                heatMapDataDto.setCount(Math.toIntExact(bucket.getDocCount()));
                heatMapDataDto.setLat(location.getLat());
                heatMapDataDto.setLng(location.getLon());
                heatMapDataDtoList.add(heatMapDataDto);
            }
        }
        return heatMapDataDtoList;
    }

    @Override
    public Long queryIndexCount(String indexName) throws IOException {
        CountRequest countRequest = new CountRequest(indexName);
        final CountResponse countResponse = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
        return countResponse.getCount();
    }


}
