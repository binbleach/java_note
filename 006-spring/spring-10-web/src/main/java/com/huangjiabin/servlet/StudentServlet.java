package com.huangjiabin.servlet;

import com.huangjiabin.entity.Student;
import com.huangjiabin.service.impl.StudentServiceImpl;


import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StudentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String id=req.getParameter("id");
        String name=req.getParameter("name");
        String email=req.getParameter("email");
        String age=req.getParameter("age");
        Student s = new Student(Integer.valueOf(id),name,email,Integer.valueOf(age));
        //获取spring容器的三种方式

        /*01自己的创建
            缺点：每次调用servlet就会创建一次容器。
        */
        //ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");


        /*02监听器ContextLoaderListener创建,手动获取。
            优点：他只会在web程序启动时创建一次容器，存放ServletContext中
            1）interface WebApplicationContext(javaWeb的容器对象) extends ApplicationContext(javaSE的容器对象)
            2）ContextLoaderListener源码：servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.context);
            注：下部分是ContextLoaderListener创建容器的获取
        */
//        String attr= WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
//        WebApplicationContext context=(WebApplicationContext)getServletContext().getAttribute(attr);

        /*03监听器ContextLoaderListener创建，工具类获取。
        */
        WebApplicationContext context=WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

        System.out.println(context);
        StudentServiceImpl service=(StudentServiceImpl) context.getBean("service");
        System.out.println(service.register(s));
        req.getRequestDispatcher("success.jsp").forward(req,resp);
    }
}
