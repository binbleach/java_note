package com.huangjiabin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/*
    内容讲解：
        1、创建一个eureka注册中心server-7001，将自己注册进去
            1）服务地址：http://localhost:7001 或 http://eureka7001.com:7001/
            2）注册地址：http://eureka7001.com:7001/eureka/
        2、创建一个服务提供者 provider-8001，注册进server-7001
        3、创建一个服务消费 consumer-80，注册进server-7001
        4、单机版eureka搭建完成。
        5、创建建一个eureka注册中心server-7002，注册进server-7001
        6、将server-7001注册自己改为注册server-7002，此时服务为集群版，注册中心相互注册,相互守望
        7、provider-8001 和 consumer-80分别 又注册进：server-7002（之前注册进server-7001不变）
        8、创建一个服务提供者provider-8002（服务名称要与provider-8001一致），同时注册进server-7001和server-7002
        9、集群版eureka搭建完成
        10、访问consumer-80的接口：http://localhost/consumer/get/1，多刷新几次会发现调用的服务不同
        11、通过设置客户端provider-8001,和provider-8002的心跳时间和恢复时间，可以观测注册中心server-7001的自我保护机制效果：
            自我保护机制下，恢复时间内注册中心就算没接收到服务发送的心跳也不会将服务删除
        12、在consumer-80 下测试负载均衡，修改负载均衡算法，手写负载均衡轮询算法
    总结：
        1、eureka停更了，知识够用就行
        2、在传统的rpc远程调用框架中，管理每个服务与服务之间依赖关系比较复杂，管理比较复杂，所以需要使用服务治理，
            管理服务于服务之间依赖关系，可以实现服务调用、负载均衡、容错等，实现服务发现与注册。
        3、Eureka包含两个组件: Eureka Server（注册中心）和Eureka Client（消费者、生产者）
            1）Eureka Server提供服务注册服务：各个微服务节点通过配置启动后，会在EurekaServer中进行注册，
              这样EurekaServer中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观看到。
            2）Eureka Client通过注册中心进行访问：是一个Java客户端，用于简化Eureka Server的交互，
              客户同时也具备一个内置的、使用轮询(round-robin)负载算法的负载均衡在应用启动后，
              将会向Eureka Server发送心跳(默认周期为30秒)。如果Eureka Server在多个小跳周期内没有接收到某个节点的心跳，
              EurekaServer将会从服务注册表中把这个服务节点移除 (默认90秒)
*/
@SpringBootApplication
@EnableEurekaServer
public class EurekaMain7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7001.class,args);
    }
}
