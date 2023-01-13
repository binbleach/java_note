package com.huangjiabin.service.impl;

import com.huangjiabin.dao.StudentDao;
import com.huangjiabin.entity.Student;
import com.huangjiabin.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {

    private StudentDao studentDao = null;

    public StudentServiceImpl() {
    }

    public StudentServiceImpl(StudentDao s){
        this.studentDao=s;
    }
    @Override
    public int addStudent(Student s) {
        int num=studentDao.insertStudent(s);
        return num;
    }

    @Override
    public List<Student> queryStudents() {
        List<Student> students=studentDao.selectStudents();
        return students;
    }

    public void setStudentDao(StudentDao studentDao) {
        System.out.println("set方法执行");
        this.studentDao = studentDao;
    }
}
