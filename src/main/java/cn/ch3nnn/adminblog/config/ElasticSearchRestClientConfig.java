package cn.ch3nnn.adminblog.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * Es 配置文件
 *
 * @Author ChenTong
 * @Date 2021/10/13 18:01
 */
@Configuration
public class ElasticSearchRestClientConfig extends AbstractElasticsearchConfiguration {

    @Override
    @Bean
    public @NotNull RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedToLocalhost()
                .build();
        return RestClients.create(clientConfiguration).rest();

    }
}
