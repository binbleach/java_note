package com.huangjiabin.springdatajpa;

import com.huangjiabin.springdatajpa.config.SpringDataJPAConfig;
import com.huangjiabin.springdatajpa.po.Customer;
import com.huangjiabin.springdatajpa.repositories.CustomerPageRepository;
import com.huangjiabin.springdatajpa.repositories.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

//基于junit4的 spring单元测试
//@ContextConfiguration(locations = "/spring.xml")
@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class _01SpringDataJpaTest {
    @Autowired
    CustomerRepository repository;
    @Autowired
    CustomerPageRepository pageRepository;

    @Test
    public void test_select(){
        Optional<Customer> byId = repository.findById(1L);
        System.out.println(byId.get());
    }

    //更新或新增。
    @Test
    public void test_save(){
        Customer customer = new Customer();
        customer.setCustName("牛");
        customer.setCustId(13L);
        repository.save(customer);
    }
    //删除，只匹配id，就算设置其他属性也不进where：delete from tb_customer where id=?
    //游历状态也可删除，因为SpringData会先帮我们做查询再删除
    @Test
    public void test_delete(){
        Customer customer = new Customer();
        customer.setCustId(11L);
        customer.setCustName("牛");
        repository.delete(customer);
    }
    @Test
    public void test_page(){
        Page<Customer> page = pageRepository.findAll(PageRequest.of(0, 5));
        System.out.println(page.getTotalPages());
        System.out.println(page.getTotalElements());
        System.out.println(page.getContent());
    }
    @Test
    public void test_sort(){
        Sort sort = Sort.by("custId").descending();
        Iterable<Customer> all = pageRepository.findAll(sort);
        System.out.println(all);
    }

    //类型安全的方式排序
    @Test
    public void test_sortTypeSafe(){
        Sort.TypedSort<Customer> typedSort = Sort.sort(Customer.class);
        Sort sort = typedSort.by(Customer::getCustId).ascending();
        Iterable<Customer> all = pageRepository.findAll(sort);
        System.out.println(all);
    }
}
