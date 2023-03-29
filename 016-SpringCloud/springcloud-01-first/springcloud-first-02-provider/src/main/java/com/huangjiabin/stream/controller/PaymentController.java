package com.huangjiabin.stream.controller;

import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.Payment;
import com.huangjiabin.stream.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/*
    内容讲解：
        一：搭建普通单机服务环境：
            1、创建普通服务 provider
            2、创建普通服务 consumer，通过restTemplate调用
*/
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        CommonResult commonResult = CommonResult.Success(payment);
        return commonResult;
    }
    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.createPayment(payment);
        if(result>0){
            return new CommonResult(200,"插入数据库成功",result);

        }else {
            return new CommonResult(505,"插入数据库失败",null);
        }
    }
}
