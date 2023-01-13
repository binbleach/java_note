package com.huangjiabin.webservice.webservice;

import com.huangjiabin.webservice.client.WeatherInterfaceImpl;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
/*
    客户端连接第二种方法：
        对比第一种，优点就是可以动态的调用webservice
*/
public class WeatherClient2 {
    public static void main(String[] args) {
        try {
            //创建WSDL文件的URL，这个地址是wsdl接口地址
            URL url=new URL("http://127.0.0.1:12345/weather?wsdl");
            //创建服务名称
            //1.namespaceURI - 命名空间地址 是wsdl里的targetNamespace
            //2.localPart - 服务视图名 是wsdl里的 service name
            QName qname=new QName("http://impl.service.webservice.huangjiabin.com/","WeatherInterfaceImplService");
            Service service=Service.create(url, qname);

            //获取服务实现类,是wsdl里的portType name
            WeatherInterfaceImpl serviceImpl= service.getPort(WeatherInterfaceImpl.class);
            //调用方法 是wsdl里的operation name
            String result=serviceImpl.queryWeather("北凉");
            System.out.println(result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
