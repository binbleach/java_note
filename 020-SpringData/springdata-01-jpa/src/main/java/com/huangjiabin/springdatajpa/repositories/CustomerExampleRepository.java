package com.huangjiabin.springdatajpa.repositories;

import com.huangjiabin.springdatajpa.po.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;


/*
1、继承QueryByExampleExecutor<Customer>必须同时继承PagingAndSortingRepository
    或CrudRepository中的其中一个，否者报错，原因不详
2、也可以直接继承JpaRepository<Customer,Long>因为JpaRepository继承了QueryByExampleExecutor
*/
public interface CustomerExampleRepository extends JpaRepository<Customer,Long> {

}
