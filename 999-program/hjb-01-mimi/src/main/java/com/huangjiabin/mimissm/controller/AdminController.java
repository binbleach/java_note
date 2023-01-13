package com.huangjiabin.mimissm.controller;

import com.huangjiabin.mimissm.pojo.Admin;
import com.huangjiabin.mimissm.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    //实现登录判断并进行跳转
    @RequestMapping("/login")
    public String login(Model model,String name, String pwd){
        Admin admin = adminService.login(name,pwd);
        if(admin!=null){
            //登录成功
            model.addAttribute("admin",admin);
            return "main";
        }else {
            model.addAttribute("errmsg","用户名或密码不正确");
            return "login";
        }

    }
}
