package com.huangjiabin.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {
    @Bean
    @LoadBalanced //开启负载均衡注解，由ribbon去实现
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
