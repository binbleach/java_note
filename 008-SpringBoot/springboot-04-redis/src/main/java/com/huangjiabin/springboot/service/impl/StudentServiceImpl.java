package com.huangjiabin.springboot.service.impl;

import com.huangjiabin.springboot.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate; //springboot提供的处理redis的类

    @Override
    public void put(String key, String value) {
        redisTemplate.opsForValue().set(key,value);//放进去的值被序列化了
    }

    @Override
    public String get(String key) {
        String value=(String)redisTemplate.opsForValue().get(key);
        return value;
    }
}
