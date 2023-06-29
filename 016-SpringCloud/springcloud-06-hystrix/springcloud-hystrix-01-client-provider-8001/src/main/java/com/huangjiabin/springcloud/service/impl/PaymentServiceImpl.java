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

    /*
        熔断器：5秒内，请求数量达到3条，且错误率达到60%打开断路器开始熔断。
             熔断10秒后断路器半开，允许一个请求通过。请求成功则关闭断路器停止熔断，失败则继续熔断10s重复以往。
    */
    @HystrixCommand(fallbackMethod = "providerCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器，默认开启
            // 指定统计滑动窗口的时间（窗口期），默认值是 10000，单位是 ms。即一个滑动窗口默认统计的是 10s 内的请求数据。
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "5000"),
            // 指定统计滑动窗口的桶数量，默认值是 10。即窗口期会被分成十份，每份1000毫秒。第11份会顶调第1份成为窗口期，2-11为窗口期
            @HystrixProperty(name = "metrics.rollingStats.numBuckets",value = "10"),
            // 记录health 快照（用来统计成功和错误率）的间隔，默认值是500，单位ms。即每500ms计算一次成功率和失败率
            @HystrixProperty(name = "metrics.healthSnapshot.intervalInMilliseconds",value = "500"),
            // 窗口期内 触发熔断的最小请求个数，默认20。建议设置为 QPS * 窗口秒数 * 60%
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "3"),
            // 窗口期内 触发熔断的请求失败率，单位是 %，默认50。
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),
            // 熔断多少秒后，断路器为半开状态，单位 ms，默认 5000
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 断路器打开后10秒内拒绝所以请求，10s后，熔断器半开，此时可以通过一个请求，成功就关闭断路器，不成功就继续打开等再10s（称为熔断时间窗口 或 活动时间窗口）
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
