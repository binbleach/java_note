package com.huangjiabin.springboot.web;

import com.huangjiabin.springboot.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
    打jar包的步骤和war包类似，把<packaging>改为jar或者不写，默认就是jar，再指定资源路径，设置打包名即可。
    运行jar，在jar包当前目录运行cmd，输入 java -jar springboot.jar(jar包名) ,即可，jdk内置有tomcat，无序在访问路径添加包名
    注：有个技巧，可以写个bat文件（在linux上则是sh文件），文件内容java -jar springboot.jar(jar包名) 那双击.bat文件也可以运行了

*/
@Controller
public class UserController {
    @RequestMapping(value = "/user/get")
    public String getUser(Model model){
        User user = new User();
        user.setId(1234);
        user.setName("徐凤年");
        model.addAttribute("user",user);
        return "userShow";
    }
    @RequestMapping(value = "user/say")
    public @ResponseBody String userSay(){
        return "你好呀，嘎劳度";
    }
}
