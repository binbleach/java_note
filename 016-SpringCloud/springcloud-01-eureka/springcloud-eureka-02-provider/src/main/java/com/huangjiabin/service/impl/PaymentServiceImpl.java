package com.huangjiabin.service.impl;

import com.huangjiabin.entity.Payment;
import com.huangjiabin.mapper.PaymentMapper;
import com.huangjiabin.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentMapper paymentMapper;
    @Override
    public int createPayment(Payment payment) {
        return 0;
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentMapper.getPaymentById(id);
    }
}
