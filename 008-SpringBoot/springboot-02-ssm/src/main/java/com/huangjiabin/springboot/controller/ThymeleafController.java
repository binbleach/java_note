package com.huangjiabin.springboot.controller;

import com.huangjiabin.springboot.domain.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/*
    设置thymeleaf模板引擎热部署：
    1、配置文件添加，spring.thymeleaf.cache=false 将模板缓存关闭
    2、和tomcat一样改设置 update resources
*/
@Controller
public class ThymeleafController {
    @RequestMapping(value="/me/say")
    public String say(Model model){
        model.addAttribute("de","我是后台数据");
        return "01message";
    }
    @RequestMapping(value = "/user/get")
    public String getUser(Model model){
        Student user = new Student();
        user.setId(1234);
        user.setName("大威天龙");
        model.addAttribute("user",user);
        return "02getUser";
    }

    @RequestMapping(value="/me/say1")
    public @ResponseBody String say1(Integer id,String name){
        return "我是后台数据id="+id+"名字="+name;
    }

    @RequestMapping(value = "/me/path")
    public String path(Model model){
        model.addAttribute("say","Hello");
        return "03pathExpression";
    }
    @RequestMapping(value = "/me/list")
    public String list(Model model){
        List<Student> list = new ArrayList<>();
        Map<String,Student> map =new HashMap<>();
        for(int i=0;i<5;i++){
            Student user = new Student();
            user.setId(0+i);
            user.setName("刘诗诗"+i);
            list.add(user);
        }
        for(int i=5;i<10;i++){

            Student user = new Student();
            user.setId(0+i);
            user.setName("刘诗诗"+i);
            map.put("k"+i,user);
        }
        model.addAttribute("list",list);
        model.addAttribute("map",map);
        model.addAttribute("sex",1);
        return "04list";
    }
    @RequestMapping(value = "/me/inline")
    public String inline(Model model, HttpServletRequest request){
        model.addAttribute("data","Hello Inline");
        model.addAttribute("flag",true);
        request.getSession().setAttribute("result","你很垃圾啊");
        request.getSession().setAttribute("data","你很垃圾啊");
        model.addAttribute("date",new Date());
        return "05inline";
    }
}
