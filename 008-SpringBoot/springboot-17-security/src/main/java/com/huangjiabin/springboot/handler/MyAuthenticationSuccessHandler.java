package com.huangjiabin.springboot.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//登录成功处理器。也可以自己设置登录失败处理器实现AuthenticationFailureHandler接口，重写onAuthenticationSuccess方法（这个方法比成功的多一个AuthenticationException参数）
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private String url;

    public MyAuthenticationSuccessHandler(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
Authentication authentication) throws IOException, ServletException {
        response.sendRedirect(url);
        //这方法可以拿到很多信息，密码却不会直接打印，会打印一个null
        User user= (User) authentication.getPrincipal();
        System.out.println(user.getUsername());//用户名admin
        System.out.println(user.getPassword());//密码 null
        System.out.println(user.getAuthorities());//权限[admin, normal]
        System.out.println(request.getRemoteAddr());//拿到客户端ip地址，你可以用手机登试试
    }
}
