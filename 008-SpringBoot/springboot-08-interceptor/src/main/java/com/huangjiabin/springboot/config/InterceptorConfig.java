package com.huangjiabin.springboot.config;

import com.huangjiabin.springboot.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //定义此类为配置文件相当于之前的xml
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    //相当于mvc:interceptors
    public void addInterceptors(InterceptorRegistry registry) {
        String [] addPathPatterns={
                "/user/**"
        };
        String [] excludePathPatterns={
                "/user/login",
                "/user/error",
                "/user/out"
        };
        registry.addInterceptor(new UserInterceptor()).addPathPatterns(addPathPatterns).excludePathPatterns(excludePathPatterns);
    }
}
