package com.huangjiabin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/*
    内容讲解：
        1、建一个服务消费者consumer-80，用eureka将该服务注册进注册中心server-7001
*/
@SpringBootApplication
@EnableEurekaClient
public class ConsumerMain80 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerMain80.class, args);
    }

}
