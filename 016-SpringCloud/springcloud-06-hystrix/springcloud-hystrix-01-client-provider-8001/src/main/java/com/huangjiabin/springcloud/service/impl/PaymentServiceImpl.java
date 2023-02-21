package com.huangjiabin.springcloud.service.impl;

import com.huangjiabin.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {

    //模拟正常访问
    @Override
    public String getInfoSuccess() {
        return "线程池："+Thread.currentThread().getName()+"，getInfoSuccess，描述：O(∩_∩)O哈哈~~";
    }

    //服务降级，fallbackMethod：降级到的方法。HystrixProperty降级属性（下面的意思是超过3秒服务降级）
    @HystrixCommand(fallbackMethod = "failHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    @Override   //模拟超时
    public String getInfoFail(Integer time) {
        //延时
        try { TimeUnit.SECONDS.sleep(time); } catch (InterruptedException e) { e.printStackTrace(); }
        //异常
        //int num = 10/0;
        return "线程池："+Thread.currentThread().getName()+"， 方法：getInfoFail，描述：耗时："+time+"秒钟";
    }
    public String failHandler(Integer time){
        return "线程池："+Thread.currentThread().getName()+"， 方法：failHandler。描述：降级了d=====(￣▽￣*)b";
    }
}
