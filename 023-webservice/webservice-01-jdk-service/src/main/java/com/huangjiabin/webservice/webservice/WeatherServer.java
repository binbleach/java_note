package com.huangjiabin.webservice.webservice;

import com.huangjiabin.webservice.service.WeatherInterface;
import com.huangjiabin.webservice.service.impl.WeatherInterfaceImpl;

import javax.xml.ws.Endpoint;

/*
    发布WebService服务
    文档地址： http://127.0.0.1:12345/weather?wsdl
*/
public class WeatherServer {
    public static void main(String[] args) {
        Endpoint.publish("http://127.0.0.1:12345/weather", new WeatherInterfaceImpl());
    }
}