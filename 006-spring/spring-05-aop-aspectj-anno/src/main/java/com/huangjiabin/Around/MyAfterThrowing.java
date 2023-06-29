package com.huangjiabin.Around;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class MyAfterThrowing {
    /*异常通知*/
    @AfterThrowing(value = "execution(* SomeServiceImpl.doSome(String,int))",throwing = "e")
    void afterThrowing(Throwable e){
        System.out.println("AfterThrowing=============="+e.toString()+"  "+e.getMessage());
    }
}
