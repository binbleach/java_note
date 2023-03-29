package com.huangjiabin.springcloud.controller;

import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.Payment;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RefreshScope
@Slf4j
public class ConsumerController {

    public static final String PAYMENT_URL="http://SPRINGCLOUD-CONFIG-PROVIDER";

    @Resource
    private RestTemplate restTemplate;

    @Value("${config.info}")
    private String config_info;


    @GetMapping("/consumer/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    @GetMapping("/consumer/getConfigInfo")
    public String getConfigInfo(){
        return config_info;
    }


}
