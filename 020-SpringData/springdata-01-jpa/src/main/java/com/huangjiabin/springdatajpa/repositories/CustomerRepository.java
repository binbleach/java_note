package com.huangjiabin.springdatajpa.repositories;

import com.huangjiabin.springdatajpa.po.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//使得Customer拥有增删查改属性
public interface CustomerRepository extends CrudRepository<Customer,Long> {
    List<Customer> findCustomerByCustName(String custName);
    Boolean existsCustomerByCustName(String custName);
    @Transactional
    @Modifying
    int deleteCustomerByCustName(String custName);
    List<Customer> findByCustNameLike(String custName);

}
