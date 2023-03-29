package com.huangjiabin.test;

import com.huangjiabin.stream.service.SomeService;
import com.huangjiabin.handler.MyInvocationHandler;
import com.huangjiabin.stream.service.impl.SomeServiceImpl;

import java.lang.reflect.Proxy;

public class TestAutoProxy {
    public static void main(String[] args) {
        SomeService service = new SomeServiceImpl();
        MyInvocationHandler factory = new MyInvocationHandler(service);
        SomeService some =(SomeService) Proxy.newProxyInstance(service.getClass().getClassLoader(),service.getClass().getInterfaces(),factory);
        some.doSome();
        some.doOther();
        System.out.println(some.getClass());
    }
}
