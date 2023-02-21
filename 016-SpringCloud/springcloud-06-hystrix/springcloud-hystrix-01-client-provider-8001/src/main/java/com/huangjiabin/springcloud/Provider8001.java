package com.huangjiabin.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
//@EnableHystrix        //两个注解都能开启Hystrix，其中@EnableHystrix包含@EnableCircuitBreaker
//@EnableCircuitBreaker
public class Provider8001 {

    public static void main(String[] args) {
        SpringApplication.run(Provider8001.class, args);
    }

}
