package com.huangjiabin.controller.c_ReturnType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huangjiabin.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class A_ReturnType {

    /* 0 1ModelAndView数据和视图*/
    @RequestMapping(value = "/returnMV.do")
    public ModelAndView returnMV(){
        ModelAndView m = new ModelAndView();
        m.setViewName("show3");
        return m;
    }

    /* 02 String视图*/
    @RequestMapping(value = "/returnString.do")
    public String returnString(){
        String url="show3";
        return url;
    }

    /*03 Void Ajax - 普通*/
    @RequestMapping(value = "/returnVoid.do")
    public void returnVoid(String name, Integer age, HttpServletResponse response) throws IOException {
        Student s = new Student(name,age);
        ObjectMapper om = new ObjectMapper();
        String ss=om.writeValueAsString(s);
        PrintWriter pw = response.getWriter();
        pw.print(ss);
    }
    /* 04 Object Ajax - 注解*/
    @RequestMapping(value = "/returnObject.do")
    @ResponseBody
    public Student returnObject(String name, Integer age){
        Student s = new Student(name,age);
        return s;
    }

    /* 05 List Ajax */
    @RequestMapping(value = "/returnList.do")
    @ResponseBody
    public List<Student> returnList(){
        ArrayList list = new ArrayList();
        Student s = new Student("范小赏",25);
        Student ss = new Student("王腾",30);
        list.add(s);
        list.add(ss);
        return list;
    }

    /*06 String 数据*/
    @ResponseBody
    @RequestMapping(value = "/returnStrings.do",produces = "text/plain;charset=utf-8") //produces设置编码
    public String returnStrings(){
        String s = "我是一个傻逼";
        return s;
    }
}
