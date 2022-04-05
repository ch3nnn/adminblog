package cn.ch3nnn.adminblog.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlusConfig 配置文件
 *
 * @MapperScan 使用MapperScan批量扫描所有的Mapper接口
 * @Author ChenTong
 * @Date 2021/9/6 19:57
 */
@Configuration
@MapperScan("cn.ch3nnn.adminblog.mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
