package com.huangjiabin.service.impl;

import com.huangjiabin.service.WeatherInterface;

import javax.jws.WebService;

//接口上的@WebService可有可无，实现类上的@WebService必须要有
@WebService
public class WeatherInterfaceImpl implements WeatherInterface {
    @Override
    public String queryWeather(String cityName) {
        System.out.println("获取城市名"+cityName);
        String weather="暴雨";
        return weather;
    }
}
