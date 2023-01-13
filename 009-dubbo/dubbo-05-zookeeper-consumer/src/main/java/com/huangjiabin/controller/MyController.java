package com.huangjiabin.controller;

import com.huangjiabin.domain.User;
import com.huangjiabin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class MyController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/user/{id}/{namey}")
    public String getUser(Model model, @PathVariable("id")Integer idy,@PathVariable("namey") String name){
        User user=userService.getUser(idy,name);
        model.addAttribute("user",user);
        return "result";
    }
}
