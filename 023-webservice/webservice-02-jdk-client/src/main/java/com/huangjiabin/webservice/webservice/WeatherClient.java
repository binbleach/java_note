package com.huangjiabin.webservice.webservice;

import com.huangjiabin.webservice.client.WeatherInterfaceImpl;
import com.huangjiabin.webservice.client.WeatherInterfaceImplService;

/*
    客户端连接第一种方法
    1）生成客户端代码，在java包下打开控制台，输入：
    wsimport -s . -p com.huangjiabin.webservice.clinet http://127.0.0.1:123456/weather?wsdl
*/
public class WeatherClient {

    public static void main(String[] args) {
        //创建服务视图，视图是从wsdl文件的service标签的name属性获取
        WeatherInterfaceImplService weatherInterfaceImplService=new WeatherInterfaceImplService();
        //获取服务实现类，实现类从wsdl文件的portType的name属性获取
        WeatherInterfaceImpl weatherInterfaceImpl=weatherInterfaceImplService.getPort(WeatherInterfaceImpl.class);
        //获取查询方法，从portType的operation标签获取
        String weather=weatherInterfaceImpl.queryWeather("北京");
        System.out.println(weather);

    }

}