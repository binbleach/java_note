package com.huangjiabin.mimissm.test;

import com.huangjiabin.mimissm.mapper.ProductInfoMapper;
import com.huangjiabin.mimissm.pojo.ProductInfo;
import com.huangjiabin.mimissm.pojo.vo.ProductInfoVo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/*
    多条件查询语句测试类
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MyTest {
    @Autowired
    ProductInfoMapper productInfoMapper;
    @Test
    public void testMD5(){
        ProductInfoVo vo = new ProductInfoVo();

        vo.setHprice(3000);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        list.forEach(p-> System.out.println(p));
    }
}
