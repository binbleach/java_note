package com.huangjiabin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
/*
    内容讲解：
        一、服务的注册与发现：
            1、@EnableEurekaClient和@EnableDiscoveryClient功能一致,都是为服务提供注册发现（连接eureka需要）。
            2、E 版本前，@EnableEurekaClient 中包含 @EnableDiscoveryClient（E版后被去除），
                官方推荐用@EnableDiscoveryClient，它还为consul或者zookeeper提供服务注册发现。
            3、E 版本后，@EnableDiscoveryClient 或@EnableEurekaClient 可省略，引入依赖即可自动配置。
                E版在spring.factories配置中将EurekaDiscoveryClientConfiguration从EnableDiscoveryClient下换到了EnableAutoConfiguration。
                同样的有zookeeper的ZookeeperDiscoveryAutoConfiguration等等
            注：E版 是 Spring Cloud Edgware，springcloud的命名根据伦敦地铁ABCD...
*/
//@EnableEurekaClient       //可省略
//@EnableDiscoveryClient    //可省略
public class PaymentMain8001 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class, args);
    }

}
