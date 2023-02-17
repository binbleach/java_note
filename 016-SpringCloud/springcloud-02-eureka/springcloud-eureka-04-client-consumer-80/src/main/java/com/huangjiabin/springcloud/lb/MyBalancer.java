package com.huangjiabin.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyBalancer implements LoadBalancer {
    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstanceList) {
        return null;
    }
}
