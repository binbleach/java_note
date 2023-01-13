package com.huangjiabin.com.handler;

import com.huangjiabin.com.exception.AgeException;
import com.huangjiabin.com.exception.NameException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/*
*异常处理controller，和普通controller一样，
* 两个注解，@ControllerAdvice在类上，@ExceptionHandler(value= 要处理抛出的异常类 )在方法上。
*传入参数Exception ex，表示处理的异常。
* */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = NameException.class)
    public ModelAndView doNameException(Exception ex){
        ModelAndView mv = new ModelAndView();

        mv.addObject("msg","姓名必须是徐凤年");
        mv.addObject("ex",ex);
        mv.setViewName("forward:/throwException.jsp");
        return mv;
    }
    @ExceptionHandler(value = AgeException.class)
    public ModelAndView doAgeException(Exception ex){
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","年龄不对");
        mv.addObject("ex",ex);
        mv.setViewName("forward:/throwException.jsp");
        return mv;
    }

    /*不带要的处理异常类的，就是如果发生其他异常就它处理*/
    @ExceptionHandler
    public ModelAndView doException(Exception ex){
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","其他异常");
        mv.addObject("ex",ex);
        mv.setViewName("forward:/throwException.jsp");
        return mv;
    }
}
