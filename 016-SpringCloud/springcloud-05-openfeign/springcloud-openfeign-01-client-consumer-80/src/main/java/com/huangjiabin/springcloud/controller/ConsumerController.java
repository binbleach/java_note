package com.huangjiabin.springcloud.controller;

import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.Payment;
import com.huangjiabin.springcloud.service.openfeign.ProviderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class ConsumerController {
/*
内容讲解：
    一、搭建feign环境：
        1、创建一个服务消费者 consumer-80，注册到 springcloud-02-eureka模块中的provider-7001和provider-7002中。
        2、启动 springcloud-02-eureka中的集群版（除consumer-80），通过feign调用服务。
        3、在provider-7001，与provider-7002中 创建一个接口，接口设置睡眠，用于测试feign调用超时情况：
            单独访问 提供者 http://localhost:8001/payment/feign/timeout虽然慢点但是能成
            通过消费者用feign调用提供者 http://localhost/consumer/timeout 超过超时时间直接报错
    二、feign的配置:
        1、配置feign：
            1）主启动类添加 @EnableFeignClients（开启feign）
            2）配置feign接口：接口类上添加@FeignClient("SPRINGCLOUD-EUREKA-PROVIDER")，接口方法与调用的controller方法一致即可
        2、配置feign的超时时间（包含连接、读取）：
            feign.client.config.SPRINGCLOUD-EUREKA-PROVIDER/default.connectTimeout/readTimeout = 1000
        3、feign日志级别：
            1）NONE: 默认的，不显示任何日志；
            2）BASIC:仅记录请求方法、URL、响应状态码及执行时间；
            3）HEADERS:除了 BASIC 中定义的信息之外，还有请求和响应的头信息；
            4）FULL:除了 HEADERS 中定义的信息之外，还有请求和响应的正文及元数据；
        4、配置feign日志：
            1）配置feign日志级别：
                1.第一种：配置 bean Logger.level
                2.第二种：在yml中配置feign.client.config.loggerLevel = FULL
            2）将feign接口的日志输出级别改为debug，springboot默认输出级别是info;
    三、feign的知识卡片：
        1、Feign：集成了Ribbon、RestTemplate实现了负载均衡的执行Http调用，只不过对原有的方式（Ribbon+RestTemplate）进行了封装，
           开发者不必手动使用RestTemplate调服务，而是定义一个接口，在这个接口中标注一个注解即可完成服务调用，这样更加符合面向接口编程的宗旨，简化了开发。
        2、OpenFeign:是SpringCloud在Feign的基础上支持了SpringMVC的注解，如@RequestMapping等。OpenFeign 的@FeignClient
           可以解析SpringMVC的@RequestMapping注解下的接口，并通过动态代理的方式产生实现类，实现类中做负载均衡并调用其他服务。
        3、java中常见的http客户端有：Apache 的 HttpClient 以及OKHttp3，还有SpringBoot自带的RestTemplate。
*/
    @Resource
    ProviderService providerService;

    @GetMapping("/consumer/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id){
        CommonResult<Payment> result = providerService.getPaymentById(new Long(1));
        log.info("你好****************");
        result.setMessage(result.getMessage()+"----------你好我是feign");
        return result;
    }

    @GetMapping("/consumer/timeout")
    public String timeout(){
        return providerService.paymentFeignTimeOut();
    }

}
