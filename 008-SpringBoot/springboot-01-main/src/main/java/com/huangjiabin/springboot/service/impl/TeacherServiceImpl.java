package com.huangjiabin.springboot.service.impl;

import com.huangjiabin.springboot.service.TeacherService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

//不配置@service,通过 在主启动类中加 @Import(TeacherServiceImpl.class)的方式注入
public class TeacherServiceImpl implements TeacherService {
    @Override
    public String query() {
        return "几点钟下班";
    }
}
