package com.huangjiabin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*
    内容讲解：
        1、启动consul：在consul.exe文件下 cmd
            运行：consul agent -dev ，服务地址：http://localhost:8500/
        2、建一个服务提供者provider-8001，将该服务注册进consul注册中心
        3、建一个消费者Consumer-80,将该服务注册进注册进consul。
        4、利用RestTemplate进行微服务调用，Consumer-80调用Provider-8001

    总结：
        分区容忍性（P）：就是保证有服务崩掉了，其它服务还能用。
            分布式微服务是必须要保证分区容错的，不然和单机有什么区别。
            所以要保证分区容错性，就必须要做数据同步，就会出现以下情况只能选一个：
                1、一致性（C）：保证一致性，慢慢同步不急。
                2、可用性（A）：先别同步了，我先取数据，不一致就不一致。
        eureka    AP   保证高可用（自我保护机制）    如：csdn，数据可以不一致，有人访问到最新的就行，迟早访问到
        zookeeper cp   保证数据的一致性            如：12306，必须要所有人同一时间内，看到的票数是一样的，慢点就慢点
        consul    cp   保证数据的一致性
        cap讲解：https://blog.csdn.net/hlzgood/article/details/110877321、https://blog.csdn.net/qq_40321119/article/details/106569498

*/
@SpringBootApplication
//@EnableDiscoveryClient    //可省略
public class ProviderMain8001 {

    public static void main(String[] args) {
        SpringApplication.run(ProviderMain8001.class, args);
    }

}
