package com.huangjiabin.mapper;

import com.huangjiabin.entity.Payment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    int createPayment(Payment payment);
    Payment getPaymentById(Long id);
}
