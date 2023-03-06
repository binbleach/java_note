package com.huangjiabin.springboot;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    1、启动zookeeper ：在zookeeper的bin目录下运行： ./zkServer.sh start
    2、查看zookeeper中注册的服务：
        1）连接zookeeper客户端：在zookeeper的bin目录下运行： ./zkCli.sh
        2）连接到客户端后运行：ls /
           你将看到三个文件夹：dubbo、services、zookeeper
           1）查看dubbo服务：ls /dubbo 2）查看普通服务： ls /services 3）查看注册中心： ls /zookeeper
    3、利用dubbo-admin（服务监控）监控zookeeper
       1）下载 dubbo-admin-0.0.1-SNAPSHOT.jar
       2）将 dubbo-admin-0.0.1-SNAPSHOT.jar 改为rar然后修改里面的\BOOT-INF\class\application.properties
            1、server.port =7001 2、spring.root.password =root 3、spring.guest.password =root
            4、spring.dubbo.registry=zookeeper://192.168.8.128:2181
       3）将rar 改为 jar，并运行jar。访问http://localhost:7001/ 即可监控zookeeper
       注意：在后续的微服务中，将微服务注册进zookeeper,并不能通过 dubbo-admin监控到
    4、还有一个ZooInspector图形化客户端工具

*/
@SpringBootApplication
@MapperScan(basePackages = "com.huangjiabin.springboot.mapper")
@EnableDubboConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
