package com.huangjiabin.springboot.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
    第一种servlet实现方式
    1、继承HttpServlet类，添加 @WebServlet注解定义请求路径
    2、在application类上添加扫描servlet类的注解@ServletCompenentScan注解

    第二种方式，通过配置类注册组件
*/
@WebServlet(urlPatterns = "/servlet") //定义请求路径
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8"); //解决乱码
        resp.getWriter().print("我是servlet");
        resp.getWriter().flush();
        resp.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
