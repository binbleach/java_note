package com.huangjiabin.springboot.filter;


import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/*
    springboot实现filter过滤器的第一种方法
    1、创建Filter实现类，重写doFilter方法，在实现类上加 @WebFilter注解
    2、在启动类上加@ServletComponentScan(basePackages="实现类上级路径") 扫描类注解。
    3、urlPatterns和value效果一致
*/
@Order(2)
@WebFilter(value = "/*")
public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("--------------进入了filter------------------");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
