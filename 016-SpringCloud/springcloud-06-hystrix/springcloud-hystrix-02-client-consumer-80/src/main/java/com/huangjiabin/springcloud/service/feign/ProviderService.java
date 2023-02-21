package com.huangjiabin.springcloud.service.feign;


import com.huangjiabin.springcloud.service.feign.hystrix.ProviderServiceCallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("SPRINGCLOUD-EUREKA-PROVIDER")
//@FeignClient(value = "SPRINGCLOUD-EUREKA-PROVIDER",fallback = ProviderServiceCallBack.class) //消费者service层降级
public interface ProviderService {

    @GetMapping("/payment/hystrix/ok")
    String paymentInfo_OK();

    @GetMapping("/payment/hystrix/timeout/{time}")
    String paymentInfo_TimeOut(@PathVariable("time") Integer id);
}
