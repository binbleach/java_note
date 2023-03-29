package com.huangjiabin.springcloud.controller;

import com.huangjiabin.entity.CommonResult;
import com.huangjiabin.entity.Payment;
import com.huangjiabin.springcloud.lb.LoadBalancer;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

/*
    内容讲解：
        一、restTemplate的负载均衡：
            1、ip端口形式调用 restTemplate不能开启负载均衡。服务名形式调用 restTemplate必须开启负载均衡。
            2、restTemplate开启负载均衡：配置bean上加@LoadBalanced(默认算法式轮询，通过ribbon实现)
            3、restTemplate的负载均衡是由ribbon实现的
        二、ribbon负载均衡：
            1、关于ribbon和负载均衡：
                1）负载均衡有两种：一种是集中式的如 nginx：客户端发起请，nginx接收反向代理给服务端。
                    另一种是进程式的如 ribbon：由ribbon向注册中心获取服务信息，经过筛选有客户端向服务端发起请求。
                2）之所以没引入依赖也能使用ribbon，是因为导入的eureka依赖带了，还有类似还有zookeeper、consul、actuator等依赖也带了。
            2、修改ribbon负载均衡算法
                1）ribbon自定义配置类不能放在@ComponentScan所扫描到的包下，否则会被共享，达不到定制化的目的。
                2）在启动类里用 @RibbonClient 去导入我们的配置类。
                3）有图片：IRule负载均衡接口调用图
            3、手写ribbon负载均衡轮询算法：
                1）注释掉@LoadBalanced，否则会报错
                2）通过discoveryClient.getInstances("SPRINGCLOUD-EUREKA-PROVIDER")获取所有实例，
                3）通过手写轮询算法选则实例接口，选则要调用的instance
                4）获取instance uri，调用即可
                5）有图片ribbon轮询算法原理图
*/
@RestController
@Slf4j
public class ConsumerController {

    //ip端口形式访问不能加@LoadBalanced
    //public static final String PAYMENT_URL = "http://localhost:8001";

    //服务名称形式访问必须加@LoadBalanced
    public static final String PAYMENT_URL="http://SPRINGCLOUD-EUREKA-PROVIDER";

    @Resource   //集群版，还需还添加注解开启负载均衡@LoadBalanced默认是轮询的
    private RestTemplate restTemplate;

    @Resource   //引入手写的负载均衡轮询算法
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

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

    //手写负载均衡
    @GetMapping("/consumer/lb")
    public CommonResult getProviderBL(){
        List<ServiceInstance> instances=discoveryClient.getInstances("SPRINGCLOUD-EUREKA-PROVIDER"); //获取指定微服务下的实例，
        if(instances == null || instances.size() <=0){
            return null;
        }
        ServiceInstance instance = loadBalancer.instance(instances);
        URI uri = instance.getUri();
        return restTemplate.getForObject(uri+"/payment/get/1",CommonResult.class);
    }

    @GetMapping("/consumer/zipkin")
    public String testZipkin(){
        String result = restTemplate.getForObject( PAYMENT_URL+"/payment/zipkin/",String.class);
        return result;
    }

}
