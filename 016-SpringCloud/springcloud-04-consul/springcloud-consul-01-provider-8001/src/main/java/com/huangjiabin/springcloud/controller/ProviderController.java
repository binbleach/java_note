package com.huangjiabin.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProviderController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/provider/test")
    public String testProvider(){
        return "我也不知道写什么"+serverPort+"\t"+ UUID.randomUUID().toString();
    }

}
