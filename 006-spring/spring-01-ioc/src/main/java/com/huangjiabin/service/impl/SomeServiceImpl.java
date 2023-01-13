package com.huangjiabin.service.impl;

import com.huangjiabin.service.SomeService;
import org.springframework.beans.factory.InitializingBean;
/*
 *   InitializingBean接口
 *   提供afterPropertiesSet方法，会在该bean创建后，无参构造方法调用后执行
 */
public class SomeServiceImpl implements SomeService, InitializingBean {
    public SomeServiceImpl(){
        System.out.println("SomeServiceImpl() 无参构造方法被调用，对象被创建");
    }
    @Override
    public void doSome() {
        System.out.println("SomeServiceImpl.doSome() 被调用了");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("SomeServiceImpl.afterPropertiesSet() 被调用");
    }
}
