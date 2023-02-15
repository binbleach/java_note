package com.huangjiabin.service;

import com.huangjiabin.entity.Payment;

public interface PaymentService {
    int createPayment(Payment payment);
    Payment getPaymentById(Long id);
}
