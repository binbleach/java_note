package com.huangjiabin.springcloud.controller;

import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/*
    内容讲解：
    1、利用 RestTemplate 调用微服务
        1）单机版：被调用的服务是普通接口
        2）集群版：被调用的服务是暴露接口
*/
@RestController
@Slf4j
public class ConsumerController {

    //集群版，还需还添加注解开启负载均衡@LoadBalanced默认是轮询的
    public static final String PAYMENT_URL="http://SPRINGCLOUD-EUREKA-PROVIDER";

    /*
        RestTemplate：是Spring提供的用于访问Rest服务的客户端模板工具类
    */
    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/create")
    public CommonResult<Payment> create(Payment payment){
        //这三个参数分别代表：REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }
    @GetMapping("/consumer/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }
}
