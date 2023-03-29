package com.huangjiabin.springcloud.controller;

import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.Payment;
import com.huangjiabin.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/*
    内容讲解：

    curl -X POST "http://localhost:8001/actuator/refresh"

*/
@RefreshScope   //动态刷新
@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Value("${config.info}")
    private String config_info;


    //提供普通查询服务
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****查询结果"+payment+"\t"+"哈哈哈哈哈哈哈哈哈");
        if(payment != null){
            return new CommonResult(200,"查询成功,serverPort:"+serverPort,payment);

        }else {
            return new CommonResult(444,"查询失败,serverPort:"+serverPort,null);
        }
    }

    @GetMapping("/payment/getConfigInfo")
    public String getConfigInfo(){
        return config_info;
    }
}
