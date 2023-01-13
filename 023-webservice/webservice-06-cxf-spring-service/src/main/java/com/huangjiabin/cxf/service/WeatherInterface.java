package com.huangjiabin.cxf.service;

import javax.jws.WebService;

//接口上的@WebService可有可无，实现类上的@WebService必须要有
@WebService
public interface WeatherInterface {
    String queryWeather(String cityName);
}
