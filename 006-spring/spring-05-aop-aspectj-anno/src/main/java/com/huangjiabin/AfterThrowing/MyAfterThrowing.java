package com.huangjiabin.AfterThrowing;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class MyAfterThrowing {
    /*异常通知*/
    @AfterThrowing(value = "execution(* SomeServiceImpl.doSome(String,int))",throwing = "e")
    void afterThrowing(Exception e){
        System.out.println("发生异常"+e.getMessage());
    }
}
