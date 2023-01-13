package com.huangjiabin.advice;


import org.aopalliance.aop.Advice;
import org.aspectj.lang.JoinPoint;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Date;

public class MyAdvice{

    void myBefore(JoinPoint j){
        System.out.println("=========Before通知功能==========");
        System.out.println("方法的定义(方法体)："+j.getSignature());
        System.out.println("方法名："+j.getSignature().getName());
        Object args[]=j.getArgs();
        for(Object a:args){
            System.out.println("方法中的参数："+a);
        }
        //切面要执行的功能代码
        System.out.println("当前日志时间"+new Date());
    }
}
