package com.huangjiabin.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements  WebMvcConfigurer {
    //跨域解决，第一种实现WebMvcConfigurer接口，重写addCorsMappings方法
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/user/**");
    }

    //第二种，配置属性返回 重写（addCorsMappings方法）的（类WebMvcConfigurer）
    /*@Bean
    public WebMvcConfigurer cors(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/user/**");
            }
        };
    }*/
}
