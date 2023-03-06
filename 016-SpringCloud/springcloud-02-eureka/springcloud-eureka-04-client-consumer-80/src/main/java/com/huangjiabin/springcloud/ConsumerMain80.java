package com.huangjiabin.springcloud;

import com.huangjiabin.myrule.MyRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
//@EnableEurekaClient   //E版后可省略
//@RibbonClient(name ="SPRINGCLOUD-EUREKA-PROVIDER",configuration = MyRuleConfig.class) //替换ribbon负载均衡算法
public class ConsumerMain80 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerMain80.class, args);
    }

}
