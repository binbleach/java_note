package com.huangjiabin.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    /*
        1、引入springCloud依赖，oauth2依赖，security依赖
        2、配置Security SecurityConfig
        3、配置授权服务器  AuthorizationServerConfig
        4、配置资源服务器 ResourceServerConfig
    */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
