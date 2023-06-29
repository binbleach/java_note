package com.huangjiabin.nacos.contriller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    内容讲解：
    一、nacos服务注册与发现：
        1、搭建nacos-server：1）安装后bin目录下执行 startup.cmd -m standalone
                            2）访问地址：http://localhost:8848/nacos
        2、创建启动 nacos-server、nacos-provider-8001、nacos-provider8002、nacos-consumer-80
        3、访问：http://localhost/consumer/nacos/1 能轮询到8001、8002则成功
    二、nacos配置中心：
        1、nacos本身实现配置中心功能，连接只需要引入nacos-config的依赖（在8001实验配置中心功能）
        2、nacos同springcloud一样，连接nacos-config的配置必须要在bootstrap.yml中，他的优先级高于application.yml
        3、@RefreshScope：springcloud的原生注解，用于实现配置的自动更新
        4、在nacos-server上添加一个配置文件。配置文件有三个属性(namespace>group>dataId)：
          1）dataId：
            ${spring.application.name}-${spring.profile.active}.${spring.cloud.nacos.config.file-extension}
            即：springcloud-alibaba-nacos-provider-dev.yaml
          2）group：默认 DEFAULT_GROUP
          3）namespace：一般用于隔离环境：开发、测试、生产等。默认public
        5、测试：启动nacos-server、provider-8001。访问：http://localhost:8001/provider/config/info
    三、nacos的数据持久化：
        1、上面所做的操作都是安装nacos后的直接操作，数据是存在内置数据库derby.集群下数据是不共享的不可用的。
        2、配置nacos的mysql数据库：
            1）mysql创建库nacos_config，执行 nacos/conf/nacos-mysql.sql 脚本，创建表
            2）在 nacos/conf/application.properties 中配置数据库连接。
    四、linux中安装配置nacos:
        1、单机版：
            1）单机版本安装配置和window一样，只是linux的mysql配置多了一个&useSSL=false（必须）
            2）单机 启动nacos：sh bin/startup.sh -m standalone
                   关闭nacos：sh bin/shutdown.sh
        2、集群版：
            1）在单机版的基础上 在 nacos/conf/cluster.conf 文件中配置集群：
                192.168.8.128 3333
                192.168.8.128 4444
                192.168.8.128 5555
            2）更改 nacos/bin/startup.sh 文件使其支持以不同端口的形式启动：sh bin/startup.sh -p 3333
                注在添加 -Dserver.port=${PORT} 的时候，教程只考虑到 "$JAVA_OPT_EXT_FIX" == "" 的时候，
                而我的配置"$JAVA_OPT_EXT_FIX" 不等于空，导致配置无效，记得考虑！！！
            3）我用的nacos.2.1.1 并不能一个解压文件启动三个nacos,复制三份：nacos、nacos2、nacos3
            4）启动三份nacos：  sh /opt/azb/nacos/nacos/bin/startup.sh -p 3333
                              sh /opt/azb/nacos/nacos2/bin/startup.sh -p 4444
                              sh /opt/azb/nacos/nacos3/bin/startup.sh -p 5555
            5）配置nginx：/usr/local/nginx/conf/nginx_nacos.conf
                upstream cluster{
                    server 127.0.0.1:3333;
                    server 127.0.0.1:4444;
                    server 127.0.0.1:5555;
                }
                server {
                    listen       1111;
                    server_name  localhost;
                    location / {
                        #root   html;
                        #index  index.html index.htm;
                        proxy_pass http://cluster;
                    }
                ...
            6）启动nginx：/usr/local/nginx/sbin/nginx -c /usr/local/nginx/conf/nginx_nacos.conf
            7）测试、访问：http://192.168.8.128:1111/nacos
               在8001中将nacos连接地址换成 192.168.8.128:1111，如果成功注册进三台nacos则成功


*/
@RestController
@RequestMapping("/provider")
@RefreshScope //在控制器类加入@RefreshScope注解使当前类下的配置支持Nacos的动态刷新功能。
public class ProviderController {
    @Value("${server.port}")
    private String serverPort;

    @Value("${config.info}")
    private String configInfo;

    @GetMapping(value = "/nacos/{id}")
    public String getPayment(@PathVariable("id") Integer id)
    {
        return "nacos registry, serverPort: "+ serverPort+"\t id"+id;
    }

    @GetMapping("/config/info")
    public String getConfigInfo() {
        return configInfo;
    }
}
