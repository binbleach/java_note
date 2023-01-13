package com.huangjiabin.handler;

import com.huangjiabin.util.ServiceTools;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
    Object target=null;
    public MyInvocationHandler(Object target){
        this.target=target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ServiceTools.doLog();
        method.invoke(target,args);
        return null;
    }
}
