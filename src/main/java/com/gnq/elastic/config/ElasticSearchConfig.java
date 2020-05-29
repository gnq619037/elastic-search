package com.gnq.elastic.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: guonanqing
 * @desc:
 * @date: 2020/5/26
 */
@Configuration
public class ElasticSearchConfig {

//    @Autowired RestClientBuilder restClientBuilder;

    @Bean
    public RestClientBuilder restClientBuilder(){
        String ipPort = "127.0.0.1:9200";
        String[] address = ipPort.split(":");
        String ip = address[0];
        int port = Integer.parseInt(address[1]);
        HttpHost httpHost = new HttpHost(ip, port, "http");
        return RestClient.builder(httpHost);
    }

    @Bean(name = "highLevelClient")
    public RestHighLevelClient highLevelClient(RestClientBuilder restClientBuilder){
        restClientBuilder.setMaxRetryTimeoutMillis(6000);
        return new RestHighLevelClient(restClientBuilder);
    }
}
