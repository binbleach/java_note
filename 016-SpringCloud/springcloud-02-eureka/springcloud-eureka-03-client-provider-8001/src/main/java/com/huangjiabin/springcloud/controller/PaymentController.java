package com.huangjiabin.springcloud.controller;

import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.Payment;
import com.huangjiabin.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource   //服务注册发现，还得到启动类上加@EnableEurekaClient或@EnableDiscoveryClient，E版后不用了
    private DiscoveryClient discoveryClient;


    //提供创建服务
    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("*****插入结果"+result);
        if(result>0){
            return new CommonResult(200,"插入数据库成功,serverPort:"+serverPort,result);

        }else {
            return new CommonResult(444,"插入数据库失败,serverPort:"+serverPort,null);
        }
    }

    //提供查询服务
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("*****查询结果"+payment+"\t"+"哈哈哈哈哈哈哈哈哈");
        if(payment != null){
            return new CommonResult(200,"查询成功,serverPort:"+serverPort,payment);

        }else {
            return new CommonResult(444,"查询失败,serverPort:"+serverPort,null);
        }
    }

    //测试获取服务信息
    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        log.info("discovery start.................................");
        //获取所有微服务
        List<String> services=discoveryClient.getServices();
        for(String element:services){
            log.info("******element:"+element);
        }
        //获取微服务下所有实例
        List<ServiceInstance> instances=discoveryClient.getInstances("SPRINGCLOUD-EUREKA-PROVIDER"); //获取指定微服务下的实例，
        for(ServiceInstance instance:instances){
            log.info(instance.getInstanceId()+"\t"+instance.getHost()+"\t"+instance.getUri());
        }
        log.info("discovery end.................................");
        return this.discoveryClient;
    }

    //用于测试feign调用超时的接口
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeOut()
    {
        System.out.println("*****paymentFeignTimeOut from port: "+serverPort);
        //暂停线程 3秒钟
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
        return serverPort;
    }
}
