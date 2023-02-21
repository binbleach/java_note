package com.huangjiabin.springcloud.controller;

import com.huangjiabin.springcloud.service.feign.ProviderService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "getInfoFailFallBackGlobal")
public class ConsumerController {

    @Resource
    ProviderService providerService;

    @RequestMapping("/consumer/hystrix/success")
    public String getInfoSuccess(){
        return providerService.paymentInfo_OK();
    }

    @HystrixCommand(      //消费者controller层降级
    fallbackMethod = "getInfoFailFallBack",
    commandProperties = {@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")}
    )
//    @HystrixCommand   //使用默认降级方法  ，不加以传参数，方法不能带参数
    @RequestMapping("/consumer/hystrix/fail/{time}")
    public String getInfoFail(@PathVariable("time") Integer time){
        return providerService.paymentInfo_TimeOut(time);
    }
    public String getInfoFailFallBack(@PathVariable("time") Integer time)
    {
        log.info(time+"");
        return "我是消费者80,controller层降级,对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,o(╥﹏╥)o";
    }
    public String getInfoFailFallBackGlobal() {
        return "我是消费者80,controller层Global降级,对方支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,o(╥﹏╥)o";
    }
}
