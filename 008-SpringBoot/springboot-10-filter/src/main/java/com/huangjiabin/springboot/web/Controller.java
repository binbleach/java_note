package com.huangjiabin.springboot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.stereotype.Controller
public class Controller {
    @RequestMapping(value = "/user/say")
    public @ResponseBody String say() {
        return "我是user的say";
    }

    @RequestMapping(value = "/consumer/say")
    public @ResponseBody String say1() {
        return "我是consumer的say";
    }
}
