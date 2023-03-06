package com.huangjiabin.springcloud.controller;

import cn.hutool.core.util.IdUtil;
import com.huangjiabin.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/*
    内容讲解：
        一、搭建feign环境
            1、创建provider-8001注册进 server-7001(单机版)，其中有两个接口，一个是正常的，一个是可调整延时的。
            2、创建一个consumer-80注册进server-7001，通过feign调用provider-8001，测试访问成功即可。
            3、测试：1）先设置延线程的延时时间为3秒，小于feign读取时间5秒。并发量小的情况下正常。
                   2）然后用jmeter调一个20000并发的请求打在服务上，并发导致线程阻塞，实际延时时间大于5秒，大量访问失败。注意：影响正常接口
        二、搭建hystrix服务降级：
            4、在provider-8001的service层开启服务降级，设置降级条件：时间超过3秒的降级。
            5、设置线程延时时间为5秒，此时达到降级条件，服务降级。设置线程延迟时间为2秒，未达到降级条件，服务不降级。
            6、测试：1）用100000并发打在服务上延时，同样达到延时条件的降级（具体延时时间不可控制），注意：不影响正常接口。
                   2）将延时改为异常 int num = 10/0 ,同样触发降级。
            7、在consumer-80的controller层开启服务降级
            8、在consumer-80的controller层开启默认服务降级
            9、在consumer-80的service层开启服务降级
        三、搭建hystrix熔断器：
            10、在provider-8001service处开启熔断器，特点是断路器打开后不再调用主逻辑，而是直接调用降级方法，且会在恢复时间内进行测试恢复（降级是每次都调用，异常才降级）
            11、创建一个hystrix的服务调用监控者dashboard-9001，监控provider-8001。当访问8001时9001会以报表和图形的形式展示（能监测断路器是情况）
        四、配置hystrix服务降级：
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
        五、配置hystrix熔断器：
            1、开启hystrix：启动类添加 @EnableHystrix 或 @EnableCircuitBreaker注解。
            2、提供者的 service层 添加@HystrixCommand 配置断路器属性并打开
        六、配置dashboard（hystrix的服务监控）：
            1、创建一个服务监控 dashboard-9001，其中访问地址：http://localhost:9001/hystrix
            2、在provider-8001启动类中配置 ServletRegistrationBean
            3、监控地址：http://localhost:8001/hystrix.stream
        七、hystrix的知识卡片：
            1、Hystrix：是一个用于处理分布式系统的延迟和容错的开源库它提供了熔断器功能，在分布式系统里，许多依赖不可避免的会调用失败，
                       比如超时、异常等，Hystrix能够保证在一个依赖出问题的情况下，不会导致整体服务失败，避免级联故障，以提高分布式系统的弹性。
                       Hystrix 具有服务降级、服务熔断、线程隔离、请求缓存、请求合并以及实时故障监控等强大功能
            2、服务降级：当程序运行异常时、服务超时、服务熔断打卡时、线程池资源耗尽时，会触发服务降级，是服务的一个备用方案。
        八、熔断器的知识卡片：
            统计用户在指定的时间范围（默认10s）之内的请求总数达到指定的数量之后，如果不健康的请求(超时、异常)占总请求数量的百分比（50%）
            达到了指定的阈值之后，就会触发熔断。触发熔断，断路器就会打开(open),此时所有请求都不能通过。在5s之后，断路器
            会恢复到半开状态(half open)，会允许少量请求通过，如果这些请求都是健康的，那么断路器会回到关闭状态(close).如果
            这些请求还是失败的请求,断路器还是恢复到打开的状态(open).
        九、dashboard的知识卡片：
            除了隔离依赖服务的调用以外，Hystrix还提供了准实时的调用监控（Hystrix Dashboard），
            Hystrix会持续地记录所有通过Hystrix发起的请求的执行信息，并以统计报表和图形的形式展示给用户，
            包括每秒执行多少请求多少成功，多少失败等。

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

    //含熔断器的接口
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuit(@PathVariable("id")Integer id){
        return paymentService.providerCircuitBreaker(id);
    }

}
