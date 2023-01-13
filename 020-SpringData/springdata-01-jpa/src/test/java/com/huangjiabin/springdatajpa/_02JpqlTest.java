package com.huangjiabin.springdatajpa;

import com.huangjiabin.springdatajpa.config.SpringDataJPAConfig;
import com.huangjiabin.springdatajpa.po.Customer;
import com.huangjiabin.springdatajpa.repositories.CustomerPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class _02JpqlTest {
    @Autowired
    CustomerPageRepository pageRepository;
    
    @Test
    public void test (){
        List<Customer> customerList = pageRepository.findCustomerByCustName_Order("徐凤年");
        System.out.println(customerList);
        Customer customer1 = new Customer();
        customer1.setCustId(9L);
        System.out.println(pageRepository.findCustomerByInfo(customer1));
        List<Customer> customerList1 = pageRepository.findCustomerByCustName_Named("徐凤年");
        System.out.println(customerList1);
    }

    @Test
    public void test_update(){
        int num = pageRepository.updateCustomerName("五", 1L);
        System.out.println(num);
    }
    @Test
    public void test_delete(){
        int num = pageRepository.deleteCustomerById(13L);
        System.out.println(num);
    }

    @Test
    public void test_insert(){
        int num = pageRepository.insertCustomerBySelect(12L);
        System.out.println(num);
    }

    @Test
    public void test_sql(){
        List<Customer> list = pageRepository.findCustomerByCustName("徐凤年");
        System.out.println(list);
    }

}
