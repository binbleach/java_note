package com.huangjiabin.springboot.service.impl;

import com.huangjiabin.springboot.mapper.StudentMapper;
import com.huangjiabin.springboot.model.Student;
import com.huangjiabin.springboot.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    public int addStudentUser(Student student){
        int num=studentMapper.insert(student);
        return num;
    }

    @Override
    public int updateStudentById(Student student) {
        int num=studentMapper.updateById(student);
        return num;
    }
}
