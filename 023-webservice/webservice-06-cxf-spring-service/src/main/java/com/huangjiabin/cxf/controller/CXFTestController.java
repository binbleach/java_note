package com.huangjiabin.cxf.controller;

import com.huangjiabin.cxf.service.WeatherInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;

/*
   测试：配置默认的cxf-servlet.xml是否会自动创建bean对象到spring容器中
   结果：并不能从ServletContext中获取到spring容器
*/
@RestController
public class CXFTestController {
    //注解注入失败
//    @Autowired
//    WeatherInterface weatherInterface;

    @RequestMapping("/test.do")
    public String test(HttpServletRequest request){

        //从ServletContext中获取 spring容器 结果 为NullPointerException
//        String attr= WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
//        WebApplicationContext context=(WebApplicationContext)request.getServletContext().getAttribute(attr);

        //cxf-servlet.xml文件兼容spring配置
        ApplicationContext context = new ClassPathXmlApplicationContext("../cxf-servlet.xml");
        WeatherInterface service = (WeatherInterface) context.getBean("weather"); //面向接口编程
        String result = service.queryWeather("北凉");
        System.out.println("hello"+result);
        return result;
    }

}
