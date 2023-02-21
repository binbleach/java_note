package com.huangjiabin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients     //开启feign
@EnableHystrix          //开启hystrix
public class Consumer80 {

    public static void main (String[] args){
        SpringApplication.run(Consumer80.class,args);
    }
}
