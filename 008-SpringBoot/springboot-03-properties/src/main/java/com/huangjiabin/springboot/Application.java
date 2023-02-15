package com.huangjiabin.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
  yml和yaml一致，都会由层级关系和properties不一样的只是展现方式不一样
  如果同时存在yml和properties那优先访问的是properties
*/
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
