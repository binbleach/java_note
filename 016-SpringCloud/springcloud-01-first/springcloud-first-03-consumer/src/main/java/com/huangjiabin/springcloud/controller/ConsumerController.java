package com.huangjiabin.springcloud.controller;

import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ConsumerController {
    //restTemplate是Spring提供的用于访问Rest服务的客户端模板工具类
    @Resource
    RestTemplate restTemplate;

    //普通调用
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
