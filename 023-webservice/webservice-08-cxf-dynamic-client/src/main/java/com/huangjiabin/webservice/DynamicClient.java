package com.huangjiabin.webservice;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import java.net.SocketException;

//cxf的动态调用
public class DynamicClient {
    //createClient方法的异常
    static int createClient_IllegalStateException_ExceptionNum=0;
    public static void main(String[] args) throws Exception {
        System.out.println("当前输出=" + Thread.currentThread().getName());
        JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
        //这里调用的是 模块：webservice-01-jdk-service 发布的服务
        Client client = clientFactory.createClient("http://127.0.0.1:12345/weather?wsdl");
        Object[] result = client.invoke("queryWeather", "北凉");
        System.out.println(result[0]);


        /*
            测试cxf动态调用的线程安全，结果：模糊不定
            1）clientFactory.createClient() 非线程安全的
            2）client.invoke() 说是new Socket()太多，端口被耗尽异常，不知道算不算线程安全。
                正常线程安全的情况下端口，不知道有没有被耗尽。
                在webservice-90-cxf-hmhn-client 中利用线程池调用，也会耗尽端口。

        */
        for(int i=0;i<1000;i++){
            Thread thread = new Thread(() -> {
                for(int j=0;j<100;j++) {
                    try {
                        //System.out.println("当前输出=" + Thread.currentThread().getName());

                        //测试 client.invoke()，端口被耗尽异常
//                        Object[] result2 = client.invoke("queryWeather", "北凉");
//                        System.out.println(result2[0]);

                        //测试正常情况下端口是否会耗尽，创建连接太慢，测试结果不理想
                        JaxWsDynamicClientFactory clientFactory2 = JaxWsDynamicClientFactory.newInstance();
                        Client client2 = clientFactory2.createClient("http://127.0.0.1:12345/weather?wsdl");
                        Object[] result2 = client2.invoke("queryWeather", "北凉");
                        System.out.println(result2[0]);
                    }catch (IllegalStateException e){
                        createClient_IllegalStateException_ExceptionNum++;
                        System.out.println("当前程序createClient_IllegalStateException_ExceptionNum："+createClient_IllegalStateException_ExceptionNum);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("当前程序存在未捕获异常！！！");
                    }
                }
            });
            thread.start();
        }


    }
}
