package com.panshi.elasticsearch.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @Auther: guo
 * @Date: 0:10 2021/4/10
 */
@EqualsAndHashCode(callSuper = true)
@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
@Data
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    private String host;
    private Integer port;
    @Bean
    @Override
    public RestHighLevelClient elasticsearchClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(host,port,"http"));
        return new RestHighLevelClient(builder);
    }

    /**
     * localhost:9300 写在配置文件中就可以了
     */
//    @Bean
//    public RestHighLevelClient highLevelClient() {
//        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo("192.168.0.101:9200")
//                //.withConnectTimeout(Duration.ofSeconds(5))
//                //.withSocketTimeout(Duration.ofSeconds(3))
//                //.useSsl()
//                //.withDefaultHeaders(defaultHeaders)
//                //.withBasicAuth(username, password)
//                // ... other options
//                .build();
//        RestHighLevelClient client = RestClients.create(clientConfiguration).rest();
//        return client;
//    }
}
