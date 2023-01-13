package com.huangjiabin.service.impl;

import com.huangjiabin.dao.StudentDao;
import com.huangjiabin.entity.Student;
import com.huangjiabin.service.StudentService;

public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao;
    @Override
    public int register(Student student) {
        int num=studentDao.insertStudent(student);
        return num;
    }

    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }
}
