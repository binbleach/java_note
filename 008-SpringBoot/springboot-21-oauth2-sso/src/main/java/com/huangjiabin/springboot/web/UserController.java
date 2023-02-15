package com.huangjiabin.springboot.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
    //获取当前用户信息
    @RequestMapping("getCurrentUser")
    public Object getCurrentUser(Authentication authentication){
        return authentication;
    }
}
