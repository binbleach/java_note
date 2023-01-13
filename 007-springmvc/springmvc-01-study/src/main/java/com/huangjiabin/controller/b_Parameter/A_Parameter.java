package com.huangjiabin.controller.b_Parameter;

import com.huangjiabin.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class A_Parameter {
    /*
    * HttpServletRequest
    * HttpServletResponse
    * HttpSession
    * 请求中所携带的请求参数
    *
    * RequestParam(value="参数名" required=true)
    *                   参数名     true必须要这个值
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
