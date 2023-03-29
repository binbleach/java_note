package com.huangjiabin.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
    内容讲解：
    一、搭建config服务端：
        1、创建 config-3344 注册进 server-7001
        2、配置config-3344 连接读取 git@gitee.com:binbleach/java_notes.git 仓库下
            016-SpringCloud/springcloud-08-config/springcloud-config 的 config-dev.yml文件
        3、测试访问 http://config3344.com:3344/master/config-dev.yml 返回其内容即成功
    二、搭建config客户端：
        4、创建 provider-8001和consumer-80 注册进server-7001
        5、配置 provider-8001和consumer-80 的config 连接读取 config-3344
        6、测试：启动 7001、3344、8001、80，访问 http://localhost:8001/payment/getConfigInfo 或
            http://localhost/consumer/getConfigInfo，可读取到git仓库下的配置文件，即成功
    三、搭建和配置config客户端的动态刷新（手动）
        7、配置：1）config客户端的bootstrap文件中配置服务暴露。
                2）config客户端需要”动态配置“的类上（必须）加 @RefreshScope
                3）在git仓库下的配置文件更新时需要执行：curl -X POST "http://localhost:8001/actuator/refresh"和
                    curl -X POST "http://localhost/actuator/refresh" ，才可以分别将客户端刷新
        8、测试： 1）启动：7001、3344、8001、80 ，更新git仓库下config-dev.yml文件，
                2）执行：curl -X POST "http://localhost:8001/actuator/refresh" 和 curl -X POST "http://localhost/actuator/refresh"
                3）访问 http://localhost:8001/payment/getConfigInfo 和 http://localhost/payment/getConfigInfo 获取的数据为更新后的数据即成功。
    四、搭建和配置config客户端的动态刷新（bus总线）
        9、有两种方式：1）通过config-server触发所有config-client（这种更好我们选这个）
                    2）通过某台config-client触发所有config-client
        10、配置：1）在config服务端的bootstrap文件中配置服务暴露。配置rabbitmq
                2）config客户端配置 rabbitmq
                3）在git仓库下的配置文件更新后，需要执行 curl -X POST "http://localhost:3344/actuator/bus-refresh"命令
        11、测试：1）启动 rabbitmq、7001、3344、8001、80，更新git仓库下config-dev.yml文件，
                2）执行：curl -X POST "http://localhost:3344/actuator/bus-refresh" 或 curl -X POST "http://localhost:3344/actuator/bus-refresh/springcloud-config-provider:8001"(只更新8001) 命令
                3）访问 http://localhost:8001/payment/getConfigInfo 和 http://localhost/payment/getConfigInfo 获取的数据为更新后的数据即成功。
    二、配置config-server：
        1、根据 ssh地址访问的，必需要公私钥。（https访问的可以不用,不过非开源需要用户名密码）
        2、码云不接受以 BEGIN OPENSSH PRIVATE KEY开头的私钥形式，需要生成以BEGIN RSA PRIVATE KEY开头的形式
           命令：ssh-keygen -m PEM -t rsa -b 4096 -f ./springcloud_config_rsa -C "springcloud-config"
        3、密钥配置可以选择配置在yml文件中（不配置默认就本地），也可以在本地.ssh文件夹下config的映射
        注：我本地的 id_rsa是OPENSSH开头，config映射的也是它。本地的springcloud_config_rsa是RSA开头，yml用的是它
    三、config访问规则：
        1、/{label}/{application}-{profile}.yml  ->  http://config3344.com:3344/master/config-dev.yml
        2、/{application}-{profile}.yml          ->  http://config3344.com:3344/config-test.yml  #（默认读取master分支）
        3、/{application}/{profile}[/{label}]    ->  http://config3344.com:3344/config/dev/master
    四、config知识卡片：
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
