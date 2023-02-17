package com.huangjiabin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
/*
    内容讲解：
        1、参考：springcloud-eureka-02-server-7001
    总结：
    1、@EnableEurekaClient和@EnableDiscoveryClient：
        1）@EnableEurekaClient和@EnableDiscoveryClient功能一致,都是为服务提供注册发现。
        2）E 版本前，@EnableEurekaClient 中包含 @EnableDiscoveryClient（E版后被去除），
            官方推荐用@EnableDiscoveryClient，它还为consul或者zookeeper提供服务注册发现。
        3）E 版本后，@EnableDiscoveryClient 或@EnableEurekaClient 可省略，引入依赖即可自动配置。
          E版在spring.factories配置中将EurekaDiscoveryClientConfiguration从EnableDiscoveryClient下换到了EnableAutoConfiguration。
          同样的有zookeeper的ZookeeperDiscoveryAutoConfiguration等等
        注：E版 是 Spring Cloud Edgware，springcloud的命名根据伦敦地图ABCD...
*/
//@EnableEurekaClient
//@EnableDiscoveryClient
public class PaymentMain8001 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class, args);
    }

}
