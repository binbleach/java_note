package com.huangjiabin.settings.web.controller;

import com.huangjiabin.settings.domain.User;
import com.huangjiabin.settings.service.UserService;
import com.huangjiabin.settings.service.impl.UserServiceImpl;
import com.huangjiabin.utils.MD5Util;
import com.huangjiabin.utils.PrintJson;
import com.huangjiabin.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//负责在页面和程序之间传输数据的，做页面的跳转。
public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String path = request.getServletPath();
        if("/settings/user/login.do".equals(path)){
            login(request,response);
        }
    }
    private void login(HttpServletRequest request,HttpServletResponse response){
        String loginAct=request.getParameter("loginAct");
        String loginPwd=request.getParameter("loginPwd");
        loginPwd = MD5Util.getMD5(loginPwd);
        String ip = request.getRemoteAddr();
        //UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        UserService us = new UserServiceImpl();
        try {
            User user=us.login(loginAct,loginPwd,ip); //这里如果抛出异常就不会执行下面的
            request.getSession().setAttribute("user",user);
            PrintJson.printJsonFlag(response,true);  //工具类能把boolean转成json传到前端
        }catch(Exception e){
            e.printStackTrace();
            String msg=e.getMessage();
            Map map = new HashMap();
            map.put("success","false");
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);  //工具类能把对象转成json并传到前端
        }
    }

}
