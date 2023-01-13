package com.huangjiabin.service.impl;

import org.springframework.beans.factory.InitializingBean;

/*
*   InitializingBean接口
*   提供afterPropertiesSet方法，会在bean创建后无参构造方法调用后后执行
*/
public class testInitializingBean implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("testInitializingBean.afterPropertiesSet() 被调用");
    }
}
