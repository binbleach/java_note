package com.huangjiabin.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//直接扫面mapper包下所有dao接口，不用一个个加@Mappe注解了（ctrl+p可查看参数，带default的可以省略参数名）
//@MapperScan(basePackages = "com.huangjiabin.springboot.mapper")
public class Application {
    //springboot项目代码必须放到Application类所在的同级目录或者下级目录
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
