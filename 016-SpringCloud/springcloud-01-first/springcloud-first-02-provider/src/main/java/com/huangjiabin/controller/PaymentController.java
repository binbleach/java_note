package com.huangjiabin.controller;

import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.Payment;
import com.huangjiabin.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


/*
    内容讲解：
        1）普通的springboot服务，给03consumer调用的
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
