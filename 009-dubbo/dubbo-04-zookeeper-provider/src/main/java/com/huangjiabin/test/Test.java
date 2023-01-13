package com.huangjiabin.test;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
/*
    测试的一种方式，不用启动web服务
*/
public class Test {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider.xml");
        context.start();
        System.in.read(); // 按任意键退出
    }
}
