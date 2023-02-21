package com.huangjiabin.springcloud.service.feign.hystrix;

import com.huangjiabin.springcloud.service.feign.ProviderService;
import org.springframework.stereotype.Component;

@Component
public class ProviderServiceCallBack implements ProviderService {
    @Override
    public String paymentInfo_OK() {
        return null;
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "我是消费者80,service层降级,对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,o(╥﹏╥)o";
    }
}
