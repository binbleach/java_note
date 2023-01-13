package com.huangjiabin;

import com.huangjiabin.service.impl.AddGoodsServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Tets_Transaction_AspectJ {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        AddGoodsServiceImpl service=(AddGoodsServiceImpl) context.getBean("add");
        System.out.println(service);
        service.buyGoods(1005,10);

    }
}
