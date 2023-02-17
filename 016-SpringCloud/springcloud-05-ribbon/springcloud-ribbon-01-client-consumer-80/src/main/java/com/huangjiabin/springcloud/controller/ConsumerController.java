package com.huangjiabin.springcloud.controller;

import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/*
    内容讲解：
    1、创建一个服务消费者consumer-80，集成到springcloud-02-eureka搭建的集群，测试ribbon负载均衡。
    2、关于ribbon和负载均衡：
        1）负载均衡有两种：一种是集中的如 nginx：客户端发起请，nginx接收反向代理给服务端。
                     另一种是进程式的如 ribbon：由ribbon向注册中心获取服务信息，经过筛选有客户端向服务端发起请求。
        2）之所以没引入依赖也能使用ribbon，是因为导入的eureka依赖带了，还有类似还有zookeeper、consul、actuator等依赖也带了。
    3、RestTemplate详解：getForObject、getForEntity...
*/
@RestController
@Slf4j
public class ConsumerController {

    public static final String PAYMENT_URL="http://SPRINGCLOUD-EUREKA-PROVIDER";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }
}
