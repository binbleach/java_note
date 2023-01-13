package com.huangjiabin.Before;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Date;

@Aspect
public class MyBefore {
    /*前置通知*/
    @Before(value = "execution(* SomeServiceImpl.do*(String,int))")
    //下面这个称为前置通知方法，可以带参数：JoinPoint.必须放在第一位
    //JoinPoint称切入点，代表业务方法。
    //如果切面功能中用到业务方法的信息参数就加
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
