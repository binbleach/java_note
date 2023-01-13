package com.huangjiabin.advice;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Date;

public class MyAdvice2 implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("=========Before2通知功能==========");
        System.out.println("方法的定义(方法体)："+method.toString());
        System.out.println("方法名："+method.getName());
        for(Object a:args){
            System.out.println("方法中的参数："+a);
        }
        System.out.println(target);
        //切面要执行的功能代码
        System.out.println("当前日志时间"+new Date());
    }
}
