package com.huangjibin.springboot.web;

import com.huangjibin.springboot.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/*


*/
@Controller
public class MyController {
    @RequestMapping(value = "/user/say")
    public @ResponseBody String say(){
        return "Hello User Say";
    }

    @RequestMapping(value = "/user/info")
    public String info(Model model){
        User user = new User();
        user.setId(1234);
        user.setName("我日你妈");
        model.addAttribute("user",user);
        return "detail";
    }
}
