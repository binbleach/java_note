package com.huangjiabin.springboot.web;

import com.huangjiabin.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class StudentController {
    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/put")
    @ResponseBody
    public String redis(String key,String value){
        studentService.put(key,value);
        return "数据添加进redis成功";
    }

    @RequestMapping(value="/get")
    public @ResponseBody Object get(String key){
        String value=studentService.get(key);
        Map<String,String> map = new HashMap<>();
        map.put("key",key);
        map.put("value",value);
        return map;
    }
}
