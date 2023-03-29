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
        2、创建nacos-provider-8001、nacos-consumer-80
    二、nacos配置中心：
        1、在nacos-server上添加一个配置文件。配置文件有三个属性(namespace>group>dataId)：
          1）dataId：
            ${spring.application.name}-${spring.profile.active}.${spring.cloud.nacos.config.file-extension}
            即：springcloud-alibaba-nacos-provider-dev.yaml
          2）group：默认 DEFAULT_GROUP
          3）namespace：默认public
        2、配置nacos-config必须要在bootstrap.yml文件中
        3、测试：启动nacos-server、provider-8001。访问：http://localhost:8001/provider/config/info
    三、nacos的数据持久化：
        1、上面所做的操作都是安装nacos后的直接操作，数据是存在内置数据库derby.集群下数据是不共享的不可用的。
        2、配置nacos的mysql数据库：
            1）mysql创建库nacos_config，执行 nacos/conf/nacos-mysql.sql 脚本，创建表
            2）在 nacos/conf/application.properties 中配置数据库连接。


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
