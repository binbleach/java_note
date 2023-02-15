package com.huangjiabin.springboot.web;

import com.huangjiabin.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
    内容讲解：
    注：跨域：只要访问的地址和源地址的 ”协议、ip、端口“ 任意一处不同，则属于跨域

*/
@Controller
public class UserController {
    @Autowired
    private User user;
    @RequestMapping("/user/getUser")
    public @ResponseBody User getUser (){
        user.setAge(20);
        user.setName("死去的王菲");
        return user;
    }
    @RequestMapping("/admin/getUser")
    public @ResponseBody String getUsera (){
        //jsonp 解决跨域返回的固定格式
        return "methodsaaa({\"name\":\"没死复活了\",\"age\":\"19\"})";
    }
}
