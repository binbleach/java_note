package com.huangjiabin.springboot.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import java.io.IOException;

/*
    第二种实现filter过滤器的方法
    1、创建Filter实现类，没有注解的。
    2、创建配置文件类
    3、这种创建方式可以配合FilterRegistrationBean设置filterRegistrationBean.setOrder(1);设置过滤器顺序

*/

public class MyFilter2 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("----------------filter222----------------");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
