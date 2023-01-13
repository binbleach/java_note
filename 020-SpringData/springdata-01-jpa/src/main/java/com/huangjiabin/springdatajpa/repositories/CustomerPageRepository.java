package com.huangjiabin.springdatajpa.repositories;

import com.huangjiabin.springdatajpa.po.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomerPageRepository extends PagingAndSortingRepository<Customer,Long> {
    @Query("From Customer where custName=?1")
    List<Customer> findCustomerByCustName_Order(String custName);

    @Query(value="from Customer  c where c.custId=:#{#customer.custId}")
    Customer findCustomerByInfo(@Param("customer") Customer customer);

    @Query(value="from Customer  c where c.custName=:aaa")
    //这里具名方式命名，你不加@Param("aaa")会报错的，就算c.custName=:aaa改成c.custName=:custName
    List<Customer> findCustomerByCustName_Named(@Param("aaa") String custName);

    @Transactional  //增删改必须加事务，不然会报错，一般加在service层
    @Modifying  //通知springdatajpa，这里是增删改的操作
    @Query("UPDATE Customer c set c.custName=:custName where c.custId=:id")
    int updateCustomerName(@Param("custName") String custName,@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Customer c where c.custId=?1")
    int deleteCustomerById(Long id);


    //jpql是不支持插入的，这是伪插入：不能直接插入值，需要插入查询到的值
    @Transactional
    @Modifying
    @Query("INSERT INTO Customer(custName) SELECT c.custName  FROM Customer c where c.custId=?1")
    int insertCustomerBySelect(Long id);

    //原生sql
    @Query(value = "select * from tb_Customer where cust_name=:custName",nativeQuery = true)
    List<Customer> findCustomerByCustName(@Param("custName") String custName);
}
