package com.huangjiabin.springcloud.controller;

import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/*
    内容讲解：
        1、参考：springcloud-eureka-02-server-7001
    总结：
        1、restTemplate：是Spring提供的用于访问Rest服务的客户端模板工具类
        2、集群版需要让 restTemplate 具有负载均衡效果，否则接口调用会失败：
            1）@LoadBalanced(用于开启负载均衡，默认算法式轮询，通过ribbon实现)
            2）@EnableEurekaClient(用于获取服务信息)
        3、关于ribbon和负载均衡：
            1）负载均衡有两种：一种是集中式的如 nginx：客户端发起请，nginx接收反向代理给服务端。
                     另一种是进程式的如 ribbon：由ribbon向注册中心获取服务信息，经过筛选有客户端向服务端发起请求。
            2）之所以没引入依赖也能使用ribbon，是因为导入的eureka依赖带了，还有类似还有zookeeper、consul、actuator等依赖也带了。
        4、自定义ribbon负载均衡算法
            1）ribbon自定义配置类不能放在@ComponentScan所扫描到的包下，否则会被共享，达不到定制化的目的。
            2）在启动类里用 @RibbonClient 去导入我们的配置类。
            3）有图片：ribbon负载均衡接口调用图
        5、有图片：ribbon轮询算法讲解

*/
@RestController
@Slf4j
public class ConsumerController {

    public static final String PAYMENT_URL="http://SPRINGCLOUD-EUREKA-PROVIDER";

    @Resource   //集群版，还需还添加注解开启负载均衡@LoadBalanced默认是轮询的
    private RestTemplate restTemplate;

    //测试微服务调用，测试 restTemplate.postForObject()
    @GetMapping(value = "/consumer/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommonResult.class);
    }
    //测试微服务调用，测试 restTemplate.getForObject()
    @GetMapping("/consumer/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommonResult.class);
    }

    //测试微服务调用，测试 restTemplate.getForEntity()
    @GetMapping("/consumer/getForEntity/{id}")
    public CommonResult<Payment> getForEntity(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new CommonResult(444,"操作失败",null);
        }
    }

}
