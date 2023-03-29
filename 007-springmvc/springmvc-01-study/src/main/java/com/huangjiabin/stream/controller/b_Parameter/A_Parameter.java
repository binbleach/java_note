package com.huangjiabin.stream.controller.b_Parameter;

import com.huangjiabin.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class A_Parameter {
    /*
      1、请求中所携带的请求参数：HttpServletRequest、HttpServletResponse、HttpSession
      2、ajax中：
        1）contentType: 告诉服务器，我要发什么类型（格式）的数据：
            application/json、 application/x-www-form-urlencoded(默认)、 text/plain、 multipart/form
        2）dataType：告诉服务器，我要想什么类型（格式）的数据：默认只能猜测：xml, json, script, or html。
      4、ajax 如果发送的是json字符串，服务端接收时必须要使用@RequestBody注解。
        始终记住：json字符串，"application/json”，@RequestBody 这三者之间是一一对应的，要有都有，要没有都没有。
      5、如果发送的是json对象，contentType不能设置为"application/json”，需使用默认的类型（application/x-www-form-urlencoded，
        为什么呢？这种类型最后还是会把json对象类型的参数转为user=username&pass=password这种形式后再发送。
      6、@RequestParam：默认的可以忽略不写，用于接收 application/x-www-form-urlencoded类型的参数，
        @RequestParam(value="参数名" required=true) required：表示请求中必须要有这个参数。
    */

    @RequestMapping(value = "/parameter.do")
    public ModelAndView parameter(HttpServletRequest request){
        String name=request.getParameter("name");
        ModelAndView m=new ModelAndView();
        m.addObject("name",name);
        m.addObject("age",0);
        m.setViewName("show2");
        return m;
    }
    /*逐个参数接收*/
    @RequestMapping(value = "/parameter2.do")
    public ModelAndView parameter(@RequestParam(value = "name",required=true) String name, Integer age){
        ModelAndView m=new ModelAndView();
        m.addObject("name",name);
        m.addObject("age",age);
        m.setViewName("show2");
        return m;
    }

    /*对象接收*/
    @RequestMapping(value = "/parameter3.do")
    public ModelAndView parameter(Student student){
        ModelAndView m=new ModelAndView();
        m.addObject("name",student.getName());
        m.addObject("age",student.getAge());
        m.setViewName("show2");
        return m;
    }

}
