package com.huangjiabin.springcloud.controller;

import com.huangjiabin.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/*
    内容讲解：
        1、将psirngcloud-02-eureka里的服务，单机启动
        2、创建provider-8001注册进 server-7001，其中有两个接口，一个是正常的，一个是延时三秒解决的。
        3、创建一个consumer-80注册进server-7001，设置feign读取时间5秒,用feign调用provider-8001。
        4、先设置延线程的延时时间为3秒，小于feign读取时间5秒，接口能访问成功
        5、然后用jmeter调一个20000并发的请求打在服务上，并发导致线程阻塞，实际延时时间大于5秒，访问失败。且影响正常接口
        6、在provider-8001处开启服务降级，设置降级条件：时间超过3秒的降级。
        7、设置线程延时时间为5秒，此时达到降级条件，服务降级。设置线程延迟时间为2秒，未达到降级条件，服务不降级。
        8、再用100000并发打在服务上延时，同样达到延时条件的降级（具体延时时间不可控制），且不影响正常接口。
        9、将延时改为异常 int num = 10/0 ,同样触发降级。
        10、在consumer-80处的controller层开启服务降级
        11、在consumer-80处的controller层开启默认服务降级
        12、在consumer-80处的service层开启服务降级
    总结：
        1、Hystrix：是一个用于处理分布式系统的延迟和容错的开源库它提供了熔断器功能，在分布式系统里，许多依赖不可避免的会调用失败，
                   比如超时、异常等，Hystrix能够保证在一个依赖出问题的情况下，不会导致整体服务失败，避免级联故障，以提高分布式系统的弹性。
                   Hystrix 具有服务降级、服务熔断、线程隔离、请求缓存、请求合并以及实时故障监控等强大功能
        2、服务降级：当程序运行异常时、服务超时、服务熔断打卡时、线程池资源耗尽时，会触发服务降级，是服务的一个备用方案。
        3、开启服务降级：
            1、在提供者 service处降级：
                1）开启hystrix：启动类添加 @EnableHystrix 或 @EnableCircuitBreaker注解。
                2）提供者的 service层 接口处 添加@HystrixCommand 配置降级条件和方法
            2、在消费者 controller处降级：
                单独降级：
                1）开启hystrix：启动类添加 @EnableHystrix 或 @EnableCircuitBreaker注解。
                2）消费者controller层 接口处 添加@HystrixCommand 配置降级条件和方法
                默认降级：
                1）开启hystrix：启动类添加 @EnableHystrix 或 @EnableCircuitBreaker注解。
                2）消费者controller层 类处 添加@DefaultProperties 配置降级方法(方法不能携带参数)
                3）需要默认降级方法的接口处添加@HystrixCommand 即可。注：不用配置条件和方法
            3、在消费者 service处降级：
                1）需要feign对hystrix的支持，不需要单独开启hystrix，不需要@HystrixEnable
                1）开启feign对hystrix的支持（开启后feign配置的读取时间会失效，回到默认超1s超时）：feign.hystrix.enable = true
                2）创建callback类，实现feign的service
                3）在feign的service 处添加 @FeignClient的 fallback属性 ，值是callback.class
*/
@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    //正常接口
    @GetMapping("/payment/hystrix/ok")
    public String paymentInfo_OK(){
        String result = paymentService.getInfoSuccess();
        log.info("*****result: "+result);
        return result;
    }

    //延时接口
    @GetMapping("/payment/hystrix/timeout/{time}")
    public String paymentInfo_TimeOut(@PathVariable("time") Integer time)
    {
        String result = paymentService.getInfoFail(time);
        log.info("*****result: "+result);
        return result;
    }

}
