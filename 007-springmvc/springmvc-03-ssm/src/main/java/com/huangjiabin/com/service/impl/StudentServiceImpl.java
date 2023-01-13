package com.huangjiabin.com.service.impl;

import com.huangjiabin.com.dao.StudentDao;
import com.huangjiabin.com.entity.Student;
import com.huangjiabin.com.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    public int addStudent(Student student) {
        int num=studentDao.insertStudent(student);
        return num;
    }

    @Override
    public List<Student> selectStudent() {
        List<Student> students = studentDao.selectStudent();
        return students;
    }
}
