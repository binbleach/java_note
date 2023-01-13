package com.huangjiabin;

import com.huangjiabin.service.BuyGoodsService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test_Transaction {
    @Test
    public void test1(){
        ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        BuyGoodsService buyGoods=(BuyGoodsService) context.getBean("buyGoods");
        buyGoods.buyGoods(1001,30);
    }

}
