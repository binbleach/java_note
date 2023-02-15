package com.huangjiabin.springboot.controller;

import com.huangjiabin.springboot.domain.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/*
    1、 jsp文件必须要编译到classpath:/META-INF/resource文件下（springboot只会编译其中的jsp文件）
    2、 spring.web.resources.static-locations 静态资源寻址（与整合jsp、thymeleaf无关，配不配置都不是关键），
    3、springboot中默认的静态资源寻址有：classpath:/resources/、classpath:/static/、
        classpath:/public/、classpath:/META-INF/resources/。并不是classpath:/。
    4、只要配置了 spring.web.resources.static-locations 或者 @EnableWebMvc 那默认配置就失效了。
    5、springboot访问静态资源：访问url必须匹配 spring.mvc.static-path-pattern值，
       且要去除掉匹配的值，在spring.web.resources.static-locations下去找，例：
       1）配置spring.web.resources.static-locations = classpath:/img/：
       http://localhost:8088/aaa/meimei3.jpg 访问成功
       2）配置spring.mvc.static-path-pattern = /img/** ：
        http://localhost:8088/aaa/img/meimei3.jpg 访问失败 ; http://localhost:8088/aaa/meimei3.jpg 访问失败
       3）配置3+配置4：
        http://localhost:8088/aaa/img/meimei3.jpg 访问成功 ; http://localhost:8088/aaa/meimei3.jpg 访问失败

 */
@Controller
public class JspController {

    @RequestMapping(value = "/say")
    public ModelAndView say() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("message","我恁爹");
        mv.setViewName("/jsp/say");
        return mv;
    }

    //也是一种写法
    @RequestMapping(value="/tall")
    public String tall(Model model){
        model.addAttribute("message","我是tall");
        return "/jsp/say";//跳转的页面，在配置文件里配置了视图解析器不用写.jsp后缀
    }
    //也是一种写法
    @RequestMapping(value="/user/getUser")
    public @ResponseBody Student getUser(){
        Student student = new Student();
        student.setId(1);
        student.setName("刘亦菲");
        student.setAge(18);
        return student;
    }
}
