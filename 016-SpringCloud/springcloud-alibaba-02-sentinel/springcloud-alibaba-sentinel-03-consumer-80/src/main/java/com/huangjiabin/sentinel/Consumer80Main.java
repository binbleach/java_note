package com.huangjiabin.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Consumer80Main {
    public static void main(String[] args) {
        SpringApplication.run(Consumer80Main.class,args);
    }
}
