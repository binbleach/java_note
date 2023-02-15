package com.huangjiabin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/*
    内容讲解：
        1、建一个服务提供者provider-7001，用eureka将该服务注册进注册中心server-7001
*/
@SpringBootApplication
@EnableEurekaClient     //开启eureka连接
@EnableDiscoveryClient  //开启服务发现,从Spring Cloud Edgware开始，@EnableDiscoveryClient 或@EnableEurekaClient 可省略
public class PaymentMain8001 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class, args);
    }

}
