package com.huangjiabin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
    内容讲解：
        1、建一个服务提供者provider-8001，将该服务注册进zookeeper注册中心
        2、创建一个消费者Consumer-80,注册进zookeeper。
        3、利用restTemplate进行微服务调用，Consumer-80调用Provider-8001
    总结：
        1、provider-8001服务注册进zookeeper的节点是临时节点，规定时间内没发心跳就会删除
*/
@SpringBootApplication
@EnableDiscoveryClient  //可省略
public class ProviderMain8001 {

    public static void main(String[] args) {
        SpringApplication.run(ProviderMain8001.class, args);
    }

}
