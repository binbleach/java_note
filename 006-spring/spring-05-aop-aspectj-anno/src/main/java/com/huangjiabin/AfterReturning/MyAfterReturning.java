package com.huangjiabin.AfterReturning;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class MyAfterReturning {
    /*后置通知*/
    @AfterReturning(value = "execution(* SomeServiceImpl.doSome(String,int))",returning = "arg")
    void afterReturning(Student arg){
        System.out.println("================AfterReturning通知方法执行==============");
        System.out.println("我是返回值"+arg);
        Student s = new Student("黄家宾",23);
        arg=s;
    }
}
