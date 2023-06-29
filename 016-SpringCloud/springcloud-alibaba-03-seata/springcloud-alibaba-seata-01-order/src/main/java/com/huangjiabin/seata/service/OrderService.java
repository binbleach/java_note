package com.huangjiabin.seata.service;


import com.huangjiabin.seata.domain.Order;

/**
 * @auther zzyy
 * @create 2019-12-11 16:48
 */
public interface OrderService {

    /**
     * 创建订单
     */
    void create(Order order);
}


