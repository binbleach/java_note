package com.huangjiabin.entity;

public class Student {
    private String name;
    private Integer age;

    public Student(){
        System.out.println("构造方法被调用");
    }
    public void setName(String name) {
        this.name = name;
        System.out.println("setName方法被调用");
    }

    public void setAge(Integer age) {
        this.age = age;
        System.out.println("setAge方法被调用");
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}
