package com.huangjiabin.springdatajpa;


import com.huangjiabin.springdatajpa.config.SpringDataJPAConfig;
import com.huangjiabin.springdatajpa.po.Customer;
import com.huangjiabin.springdatajpa.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;


/*
    方法名查询：通过hibernate定义的特殊名词组合来查询
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringDataJPAConfig.class)
public class _03MethodNameTest {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void test (){
        List<Customer> list = customerRepository.findCustomerByCustName("徐凤年");
        System.out.println(list);
    }
    @Test
    public void test2 (){
        Boolean flag = customerRepository.existsCustomerByCustName("徐凤年");
        System.out.println(flag);
    }
    @Test
    public void test3 (){
        int num = customerRepository.deleteCustomerByCustName("牛逼");
        System.out.println(num);
    }
    @Test
    public void test4 (){
        List<Customer> byCustNameLike = customerRepository.findByCustNameLike("徐%");
        System.out.println(byCustNameLike);
    }
}
