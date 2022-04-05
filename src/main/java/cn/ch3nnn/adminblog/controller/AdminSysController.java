package cn.ch3nnn.adminblog.controller;


import cn.ch3nnn.adminblog.common.ResultCode;
import cn.ch3nnn.adminblog.dto.HeatMapDataDto;
import cn.ch3nnn.adminblog.service.SearchService;
import cn.ch3nnn.adminblog.utils.OSUtils;
import io.swagger.annotations.ApiOperation;
import org.hyperic.sigar.SigarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 服务器相关信息
 *
 * @author Chentong
 */
@Controller()
@RequestMapping("/admin/sysInfo")
public class AdminSysController {


    private static final LinkedList<Map<String, Object>> MEMORY_LINKED_LIST = new LinkedList<>();

    @Autowired
    private SearchService searchService;

    @ApiOperation("加载admin/sysInfo.html")
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String index() {
        return "admin/sysInfo";
    }

    /**
     * 获取服务器内存 cup 等数据信息
     */
    @PostMapping("/sys_info")
    @ResponseBody
    public List<Map<String, Object>> sysInfo() {
        try {
            // 获取服务器内存数据
            Map<String, Object> stringObjectMap = new HashMap<>(10);
            HashMap<String, Object> memory = OSUtils.memory();
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            stringObjectMap.put(sdf.format(calendar.getTime()), memory);
            MEMORY_LINKED_LIST.add(stringObjectMap);
            if (MEMORY_LINKED_LIST.size() > 7) {
                MEMORY_LINKED_LIST.remove();
            }
            return MEMORY_LINKED_LIST;
        } catch (SigarException e) {
            e.printStackTrace();
            return null;
        }
    }


    @ApiOperation("获取ip热力图")
    @RequestMapping(value = "ipHeatmap", method = RequestMethod.GET)
    @ResponseBody
    public ResultCode ipHeatmap() {
        try {
            final HashMap<String, Object> resHashMap = new HashMap<>(10);
            final List<HeatMapDataDto> heatMapDataDtoList = searchService.queryIpHeatMap();
            final Long count = searchService.queryIndexCount("filebeat-7.6.2");
            resHashMap.put("count", count);
            resHashMap.put("heatMapDataDtoList", heatMapDataDtoList);
            return ResultCode.success(resHashMap);
        } catch (Exception e) {
            return ResultCode.error("Elasticsearch: " + e.getMessage());
        }
    }
}