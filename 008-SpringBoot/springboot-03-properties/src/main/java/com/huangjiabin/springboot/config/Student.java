package com.huangjiabin.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
    配置文件的读取方式一：
    通过@Component和@ConfigurationProperties()注解去映射
    prefix = "student" 属性可以区别同名配置
*/
@Component
@ConfigurationProperties(prefix = "student")
public class Student {
    private String name;
    private String wbsit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWbsit() {
        return wbsit;
    }

    public void setWbsit(String wbsit) {
        this.wbsit = wbsit;
    }
}
