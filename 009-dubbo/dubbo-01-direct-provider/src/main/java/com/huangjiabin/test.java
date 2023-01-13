package com.huangjiabin;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/*
    不用配置web，不用启动tomcat。更专注与dubbo代码
    直接通过测试类：启动spring容器，加载bean对学校。去测试提供者。当然，消费者也可以这样
*/
public class test {
    public static void main(String[] args) throws IOException {
        ApplicationContext context=new ClassPathXmlApplicationContext("provider.xml");
        //启动容器，加载bean对象
        ((ClassPathXmlApplicationContext)context).start();
        //阻塞操作，让应用一直执行下去
        System.in.read();
    }
}
