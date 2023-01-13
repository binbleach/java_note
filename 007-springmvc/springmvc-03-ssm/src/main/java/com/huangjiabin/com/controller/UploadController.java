package com.huangjiabin.com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("uploadController")
public class UploadController {
    @RequestMapping("/uploadController.do")
    public String uploadController(){
        return "uploadController/uploadController";
    }
}
