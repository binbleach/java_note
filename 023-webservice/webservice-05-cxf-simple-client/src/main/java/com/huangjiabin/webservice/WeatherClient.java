package com.huangjiabin.webservice;

import com.huangjiabin.client.WeatherInterface;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;


public class WeatherClient {
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(WeatherInterface.class);
        factoryBean.setAddress("http://127.0.0.1:8080/weather");
//        WeatherInterface weatherInterface = factoryBean.create(WeatherInterface.class);
        WeatherInterface weatherInterface = (WeatherInterface) factoryBean.create();
        String result = weatherInterface.queryWeather("北京");
        System.out.println(result);

        //服务端添加输入输出拦截器
        Client client = ClientProxy.getClient(weatherInterface);
        client.getInInterceptors().add(new LoggingInInterceptor());
        client.getOutInterceptors().add(new LoggingOutInterceptor());



    }
}
