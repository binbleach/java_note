package com.huangjiabin.Around;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
/*环绕通知*/
@Aspect
public class MyAround {
    /*
     * 环绕通知方法的定义格式
     *  1.public
     *  2.必须有一个返回值，推荐使用Object
     *  3.方法名称自定义
     *  4.方法有参数，固定的参数 ProceedingJoinPoint
     */
    @Around(value = "execution(* SomeServiceImpl.doSome(String,int))")
    /*
     * 特点：
     *   1.它是功能最强的通知
     *   2.在目标方法的前和后都能增强功能。
     *   3.控制目标方法是否被调用执行
     *   4.修改原来的目标方法的执行结果。 影响最后的调用结果
     */
    void around(ProceedingJoinPoint pjp) throws Throwable { //特有固定参数,ProceedingJoinPoint继承了JoinPoint.
        System.out.println("================Around通知方法执行==============");
        Object someService= pjp.proceed(); //执行目标方法，相当于method.invoke(target,args)
        System.out.println("================Around通知方法执行==============");
    }
}
