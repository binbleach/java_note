package com.huangjiabin.cxf.controller;

import com.huangjiabin.cxf.clinet.WeatherInterface;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class WeatherController {
    @Resource
    WeatherInterface weatherInterface;
    @RequestMapping("/getWeather.do")
    public String getWeather(){
        String result = weatherInterface.queryWeather("北凉");
        System.out.println(result);
        return result;
    }
}
