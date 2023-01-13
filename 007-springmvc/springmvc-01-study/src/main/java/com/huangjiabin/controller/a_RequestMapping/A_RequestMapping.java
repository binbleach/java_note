package com.huangjiabin.controller.a_RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class A_RequestMapping{

    /*
    *RequestMapping(放在类上就是公共名称)
    * value属性：值是带根目路的路径。
    * method属性：值是RequestMethod的枚举类型，RequestMethod.GET是get请求，RequestMethod.POST是post请求。
    *             不加是get，post都可以。
    * */

    @RequestMapping(value = "/some.do",method = RequestMethod.GET)
    public ModelAndView some(){
        ModelAndView m=new ModelAndView();
        m.addObject("key1","这里可以是对象");
        m.addObject("key2","hellosSpringMvc");
        m.setViewName("show");
        return m;
    }

    @RequestMapping(value="/other.do")
    public ModelAndView other(){
        ModelAndView m=new ModelAndView();
        m.addObject("key1","这里可以是对象");
        m.addObject("key2","hellosSpringMvc");
        m.setViewName("show");
        return m;
    }
}
