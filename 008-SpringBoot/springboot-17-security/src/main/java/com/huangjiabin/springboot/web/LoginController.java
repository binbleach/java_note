package com.huangjiabin.springboot.web;

import org.springframework.http.HttpRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    //已经不需要了，在第7步你会发现根本不走这里，需要设置登录成功后跳转页面。
    /*@RequestMapping("/login")
    public String login(){
        System.out.println("执行登录方法");
        return "redirect:/main.html";
    }*/
    //判断角色注解
    //@Secured("ROLE_abc") //的先再配置类里拦截登录哈，然后再判断是否有权限才放行，否则报500，如果满足配置类里权限这里也不会验了
    //判断权限注解和access(“权限表达式”)类似
    @PreAuthorize("hasRole('abc')")//和上面注解功能一致，这里比配置类和上面注解强大一点可以写ROLE_abc，也可以写abc
    @RequestMapping("/toMain")
    public String toMain(HttpServletRequest request){
        System.out.println("进入toMain");
        System.out.println(request.getContextPath());
        System.out.println(request.getRequestURL());
        return "redirect:main.html";
    }
    @RequestMapping("/toError")
    public String toError(){
        System.out.println("进入toError");
        return "error";
    }
    @RequestMapping("/demo")
    public @ResponseBody String demo(){
        return "demo";
    }

    //页面跳转
    @RequestMapping("/showLogin")
    public String demo1(){
        return "/static/login";
    }

}
