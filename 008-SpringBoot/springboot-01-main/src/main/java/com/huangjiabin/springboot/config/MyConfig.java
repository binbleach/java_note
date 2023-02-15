package com.huangjiabin.springboot.config;


import com.huangjiabin.springboot.domain.Student;
import com.huangjiabin.springboot.domain.Teacher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration(proxyBeanMethods = false)   //Lite模式，不开启代理bean
public class MyConfig {

    @Bean
    public Teacher getTeacher(){
        Teacher teacher = new Teacher("1", "王秀");
        teacher.setStudents(getStudent());  //如果proxyBeanMethods = false，这里爆红没关系，springboot版本导致的
        return teacher;
    }

    @Bean
    public Student getStudent(){
        return new Student("1","帅哥","1");
    }
}
