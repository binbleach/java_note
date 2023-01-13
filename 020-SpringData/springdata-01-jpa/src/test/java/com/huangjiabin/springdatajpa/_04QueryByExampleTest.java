package com.huangjiabin.springdatajpa;


import com.huangjiabin.springdatajpa.config.SpringDataJPAConfig;
import com.huangjiabin.springdatajpa.po.Customer;
import com.huangjiabin.springdatajpa.repositories.CustomerExampleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/*
   Query By Example：
    1、不支持嵌套或分组的属性约束，如：firstName = ?0 or (firstname = ?1 and lastname = ?2)
    2、只支持字符串 start（开头匹配）/contains（包含匹配）/ends/regex（正则匹配）匹配和其他属性类型的精准匹配

*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringDataJPAConfig.class)
public class _04QueryByExampleTest {
    @Autowired
    CustomerExampleRepository customerExampleRepository;

    /*
        简单查询
    */
    @Test
    public void test01(){
        //这里实际项目中，Customer对象是由前端传过来的
        Customer customer = new Customer();
        customer.setCustName("张三");

        //构建查询条件
        Example<Customer> example = Example.of(customer);


        List<Customer> customerList = customerExampleRepository.findAll(example);
        System.out.println(customerList);
    }

    /*
        条件匹配器进行查询
    */
    @Test
    public void test02(){
        //这里实际项目中，Customer对象是由前端传过来的
        Customer customer = new Customer();
        customer.setCustName("张三");

        //构建查询条件
        Example<Customer> example = Example.of(customer);


        List<Customer> customerList = customerExampleRepository.findAll(example);
        System.out.println(customerList);
    }
}
