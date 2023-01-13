package com.huangjiabin.service.impl;

import com.huangjiabin.service.SomeService;

public class SomeServiceImpl implements SomeService {
    @Override
    public void doSome() {
        //如果多年后要在此增加日志，下面的doOther也要增加，代码耦合度高，重复高麻烦。所以就有了动态代理解决
        //ServiceTools.doLog(); //切面Aspect 功能
        System.out.println("===============doSome业务方法================");
    }

    @Override
    public void doOther() {
        System.out.println("===============doOther业务方法================");
    }
}
