package com.huangjiabin.stream.service;

import agent.TaoBao;
import factory.UsbAppleFactory;
import factory.UsbKingFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;


/*
    jdk不能代理类：
        因为会用反射生成 (tao)$proxy0 extends Proxy implement USB
        如果USB是类的话，那 tao extends Proxy,USB就错了，因为java的类是单继承的
    cglib实现动态代理的逻辑是使用子类继承代理类，就没有单继承的限制了。
*/
public class MainShop {
    public static void main(String[] args) {
        USB kingUsb = new UsbKingFactory();
        InvocationHandler taoBao = new TaoBao(kingUsb);
        USB kingProxy=(USB) Proxy.newProxyInstance(kingUsb.getClass().getClassLoader(),kingUsb.getClass().getInterfaces(),
                taoBao);
        System.out.println(kingProxy.price(1));
        System.out.println(kingProxy.getClass().getName()); //com.sun.proxy.$Proxy0

        System.out.println("================================");
        USB appleUsb = new UsbAppleFactory();
        InvocationHandler taoBao1 = new TaoBao(appleUsb);
        USB appleProxy=(USB) Proxy.newProxyInstance(appleUsb.getClass().getClassLoader(),appleUsb.getClass().getInterfaces(),
                taoBao1);
        System.out.println(appleProxy.price(1));
        System.out.println(appleProxy.getClass().getName());
    }
}
