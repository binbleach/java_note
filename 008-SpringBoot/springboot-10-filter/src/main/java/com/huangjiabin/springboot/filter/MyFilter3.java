package com.huangjiabin.springboot.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter3 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("----------------filter333----------------");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
