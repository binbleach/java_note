package com.huangjiabin.springboot.service.impl;

import com.huangjiabin.springboot.service.StudentService;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Override
    public String query() {
        return "今天中午吃啥子，今天晚上吃啥子，明天早上吃啥子";
    }
}
