package com.huangjiabin.webservice.test;

import com.huangjiabin.webservice.impl.WeatherCall;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    static int createClient_IllegalStateException_ExceptionNum =0;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        WeatherCall weatherCall = (WeatherCall) context.getBean("weatherCall");
        String result = weatherCall.invoke("queryWeather", "北凉");
        System.out.println(result);

        //测试线程安全，结果和 webservice-08-cxf-dynamic-client一样 会报端口耗尽异常
        for(int i=0;i<1000;i++){
            Thread thread = new Thread(() -> {
                for(int j=0;j<1000;j++) {
                    try {
                        WeatherCall weatherCall2 = (WeatherCall) context.getBean("weatherCall");
                        String result2 = weatherCall2.invoke("queryWeather", "北凉");
                        System.out.println(result2);
                    }catch (IllegalStateException e){
                        createClient_IllegalStateException_ExceptionNum++;
                        System.out.println("当前程序createClient_IllegalStateException_ExceptionNum："+createClient_IllegalStateException_ExceptionNum);
                    }catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("当前程序存在未捕获异常！！！");
                    }
                }
            });
            thread.start();
        }
    }
}
