package com.huangjiabin.sentinel.feign;

import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "cloudalibaba-sentinel-provider",fallback = ConsumerFeignGlobalFallback.class)
public interface ConsumerFeignService {
    @GetMapping(value = "/provider/query/{id}")
    CommonResult<Payment> query(@PathVariable("id") Long id);
}
