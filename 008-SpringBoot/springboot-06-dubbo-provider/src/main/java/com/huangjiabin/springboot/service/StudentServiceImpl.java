package com.huangjiabin.springboot.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.huangjiabin.springboot.mapper.StudentMapper;
import com.huangjiabin.springboot.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = StudentService.class,version = "1.0.0",timeout = 15000)
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public Student queryStudentById(Integer id) {
        Student student=studentMapper.selectByPrimaryKey(id);
        return student;
    }

    @Override
    public int queryAllStudentCount() {
        Integer num = (Integer) redisTemplate.opsForValue().get("allStudentCount");

        if(null==num) {
            num = studentMapper.selectAllStudentCount();                    //设置时间
            redisTemplate.opsForValue().set("allStudentCount",num,30, TimeUnit.SECONDS);
        }
        return num;
    }
}
