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

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource   //服务发现,还得到启动类上加@EnableDiscoveryClient
    private DiscoveryClient discoveryClient;


    //@RequestBody能将接收的json对象转成自定义对象，再加这里表单提交就不行了
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

    /*
    *   用于对外暴露服务信息
    */
    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        //获取所有微服务
        List<String> services=discoveryClient.getServices();
        for(String element:services){
            log.info("******element:"+element);
        }
        //获取微服务下所有实例
        List<ServiceInstance> instances=discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE"); //获取指定微服务下的实例，
        for(ServiceInstance instance:instances){
            log.info(instance.getInstanceId()+"\t"+instance.getHost()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }
}
