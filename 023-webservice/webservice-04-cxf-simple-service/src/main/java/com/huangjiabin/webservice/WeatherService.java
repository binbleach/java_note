package com.huangjiabin.webservice;

import com.huangjiabin.stream.service.WeatherInterface;
import com.huangjiabin.stream.service.impl.WeatherInterfaceImpl;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
/*
    优点：可以更好地控制行为。例如，可以添加日志记录拦截器：
*/
public class WeatherService {
    public static void main(String[] args) {
        JaxWsServerFactoryBean factoryBean = new JaxWsServerFactoryBean();
        factoryBean.setServiceClass(WeatherInterface.class);
        factoryBean.setAddress("http://localhost:8080/weather");
        factoryBean.setServiceBean(new WeatherInterfaceImpl());
        //设置日志，没搞懂
        factoryBean.getFeatures().add(new LoggingFeature());
        //输入拦截器（拦截请求）
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        //输出拦截器（拦截相应）
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        factoryBean.create();



    }
}
