package com.huangjiabin.springboot.domain;

import java.util.ArrayList;
import java.util.List;

public class Teacher {
    private String id;
    private String name;
    private List<Student> students;

    public Teacher(String id, String name) {
        this.id = id;
        this.name = name;
        this.students = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    public void setStudents(Student students) {
        this.students.add(students);
    }
}
