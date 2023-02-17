package com.huangjiabin.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/*
    内容讲解：
        1、参考 springcloud-consul-01-provider-8001
*/
@RestController
@Slf4j
public class ConsumerController {
    public static final String PAYMENT_URL="http://AAA"; //大小写无所谓

    @Resource   //集群版，还需还添加注解开启负载均衡@LoadBalanced默认是轮询的
    private RestTemplate restTemplate;

        @GetMapping("/consumer/test")
    public String testConsumer(){
        return restTemplate.getForObject(PAYMENT_URL+"/provider/test",String.class);
    }
}
