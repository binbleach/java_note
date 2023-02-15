package com.huangjiabin.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.huangjiabin.springboot.servlet") //扫描servlet注解
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
