package com.huangjiabin.springboot.web;

import com.huangjiabin.springboot.config.Student;
import com.huangjiabin.springboot.config.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class configObject {
    //第一种读取配置文件的方式
    @Autowired
    private Student student;
    //第二种读取配置文件的方式
    @Resource
    private User user;
    //第三种读取配置文件的方式
    @Autowired
    private Environment environment;
    /*第四种读取（嵌套类的形式）其实是第一种，
        1、参考：server.port=8080，server.servlet.context-path=/dev
        2、ServerProperties类有注解@ConfigurationProperties(prefix = "server")
        3、ServerProperties类有一个属性：private Integer port;
        4、ServerProperties类有一个内部类Servlet
        5、Servlet类里有一个属性private String contextPath;
    */
    /*
        第五种：
        @PropertySource注解，可以读默认配置文件外的配置文件，也是配合@Value使用
    */

    @RequestMapping(value = "/say")
    public @ResponseBody String say(){
        return "学校："+student.getName()+"网站："+student.getWbsit();
    }

    @RequestMapping(value="/say2")
    public @ResponseBody String say2(){
        System.out.println(user.getName());
        return "用户名2："+user.getName()+"====="+"用户年龄2"+user.getAge();

    }
    @RequestMapping(value="/say3")
    public @ResponseBody String say3(){
        System.out.println(user.getName());
        //第三种读取配置文件的方式
        return "用户名3："+environment.getProperty("user1.name")+"====="+"用户年龄3"+environment.getProperty("user1.age");

    }
}
