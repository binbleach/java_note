package com.huangjiabin.springcloud.controller;

import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/*
    内容讲解：
        1） 通过 RestTemplate 去调用02provider
        2） 这种调用有弊端，集群变多了的话，服务调用没有治理。所以需要eureka
*/
@RestController
public class ConsumerController {
    @Resource
    RestTemplate restTemplate;

    //单机版
    public static final String PAYMENT_URL="http://localhost:8001";

    @RequestMapping("/consumer/create")
    public CommonResult<Payment> create(Payment payment,HttpServletRequest request){
        //这三个参数分别代表：REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }

    @GetMapping("/consumer/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }
}
