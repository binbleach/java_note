package com.huangjiabin.springboot.config;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    /***
     * 定义方法，返回的返回值是java 对象，这个对象是放入到spring容器中
     * 使用@Bean修饰方法
     * @Bean等同于<bean></bean>
     */
    //过时
    /*@Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }*/
    //新版
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }
}