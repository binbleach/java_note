package com.huangjiabin.springboot.controller;

import com.huangjiabin.springboot.domain.Student;
import com.huangjiabin.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MybatisController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/student/{id}")
    public @ResponseBody Object student(@PathVariable Integer id){
        Student student = studentService.queryStudentById(id);
        return student;
    }
}
