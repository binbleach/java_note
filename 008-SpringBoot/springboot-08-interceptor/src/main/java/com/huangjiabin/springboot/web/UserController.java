package com.huangjiabin.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/*
    springboot中实现拦截器只需两步
    1、拦截器类 继承 HandlerInterceptor 接口
    2、配置文件类 添加@Configuraiton ,重写 addInterceptors 方法
*/
@Controller
@RequestMapping(value = "/user")  //不加这个要报错不知道为什么
public class UserController {

    @RequestMapping(value = "/login")
    public @ResponseBody String login(HttpServletRequest request){
        request.getSession().setAttribute("user","a");
        return "登录成功";
    }

    //登录后才可访问
    @RequestMapping(value = "/center")
    public @ResponseBody String center(){
        return "I'm center";
    }

    //不登录也可访问
    @RequestMapping(value = "/out")
    public @ResponseBody String out(){
        return "I'm out";
    }

    //错误
    @RequestMapping(value= "/error")
    public @ResponseBody String error(){
        return "I'm error";
    }
}
