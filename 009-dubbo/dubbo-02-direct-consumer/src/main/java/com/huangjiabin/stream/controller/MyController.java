package com.huangjiabin.stream.controller;


import com.huangjiabin.domain.User;
import com.huangjiabin.stream.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class MyController {
    @Resource
    private UserService userService;

    @RequestMapping(value="/user")
    public String getUserById(Model model) {
        User user=userService.getUserById();
        model.addAttribute("user",user);
        return "result";
    }
}
