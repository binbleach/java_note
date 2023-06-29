package com.huangjiabin.sentinel.feign;

import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.Payment;
import org.springframework.stereotype.Component;

/*
    全局限流降级处理
*/
@Component
public class ConsumerFeignGlobalFallback implements ConsumerFeignService{
    @Override
    public CommonResult<Payment> query(Long id) {
        return new CommonResult<>(44444,"全局服务降级返回,---PaymentFallbackService",new Payment(id,"errorSerial"));
    }
}
