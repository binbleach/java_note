package com.huangjiabin.springcloud.service.impl;

import com.huangjiabin.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import cn.hutool.core.util.IdUtil;

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

    //熔断器
    @HystrixCommand(fallbackMethod = "providerCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器，默认开启
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "5000"),// 5s内 （统计时间窗口期）
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "3"),// 请求达到3条 （统计时间窗口内的请求阈值）
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 错误率达到60% 触发跳闸。此时断路器打开 （统计时间窗口内的错误数量百分比阈值）
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 断路器打开后，每隔10秒，尝试一次请求 此时熔断器半开，请求成功则关闭断路器（恢复时间窗口期）
    }
    )
    public String providerCircuitBreaker(Integer id){
        if(id<0){
            throw new RuntimeException("*********id不能为负数");
        }
        String s = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + s;

    }

    public String providerCircuitBreaker_fallback(@PathVariable("id") Integer id)
    {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id+"流水号："+IdUtil.simpleUUID();
    }
}
