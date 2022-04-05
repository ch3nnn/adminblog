package cn.ch3nnn.adminblog.service;

import cn.ch3nnn.adminblog.dto.HeatMapDataDto;

import java.io.IOException;
import java.util.List;

/**
 * @Author ChenTong
 * @Date 2021/10/15 09:22
 */
public interface SearchService {


    /**
     * 查询nginx请求ip地址 维度、数量
     *
     * @return
     * @throws Exception
     */
    List<HeatMapDataDto> queryIpHeatMap() throws Exception;


    /**
     * 查询索引总数据
     *
     * @param indexName 索引名称 或者 别名
     * @return 数量
     * @throws IOException
     */
    Long queryIndexCount(String indexName) throws IOException;


}
