package com.huangjiabin.springcloud.service;

import com.huangjiabin.entity.Payment;
import org.apache.ibatis.annotations.Param;



public interface PaymentService {
    int create(Payment payment);
    Payment getPaymentById(@Param("id") Long id);
}
