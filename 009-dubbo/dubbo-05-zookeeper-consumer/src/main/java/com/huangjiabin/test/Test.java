package com.huangjiabin.test;

import com.huangjiabin.domain.User;
import com.huangjiabin.stream.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        UserService userService = (UserService)context.getBean("userService"); // 获取远程服务代理
        User user = userService.getUser(1,"徐凤年"); // 执行远程方法
        System.out.println( user ); // 显示调用结果

        UserService userService2 = (UserService)context.getBean("userService2"); // 获取远程服务代理
        User user2 = userService2.getUser(1,"徐凤年"); // 执行远程方法
        System.out.println( user2 ); // 显示调用结果
    }
}
