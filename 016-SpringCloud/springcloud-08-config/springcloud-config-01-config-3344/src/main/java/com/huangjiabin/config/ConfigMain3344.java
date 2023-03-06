package com.huangjiabin.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
    内容讲解：
    一、config知识卡片：
        SpringCloud Config为微服务架构中的微服务提供集中化的外部配置支持，配置服务器为各人不同微服务应用的
        所有环境提供了一个中心化的外部配置。

 */
@SpringBootApplication
@EnableConfigServer     //激活配置中心
public class ConfigMain3344
{
    public static void main( String[] args )
    {
        SpringApplication.run(ConfigMain3344.class,args);
    }
}
