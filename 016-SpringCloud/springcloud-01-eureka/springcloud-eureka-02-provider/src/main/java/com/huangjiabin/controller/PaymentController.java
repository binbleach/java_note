package com.huangjiabin.controller;

import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.Payment;
import com.huangjiabin.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "/get/getPayment/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        CommonResult commonResult = CommonResult.Success(payment);
        return commonResult;
    }
}
