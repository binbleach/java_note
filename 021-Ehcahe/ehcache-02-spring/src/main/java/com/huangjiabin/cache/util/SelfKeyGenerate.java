package com.huangjiabin.cache.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/*
    自定义缓存数据key的生成策略
*/
@Component("selfKeyGenerate")
public class SelfKeyGenerate implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        //JSON.toJSONString()是alibaba的
//        return target.getClass().getSimpleName() + "#" + method.getName()
//            + "(" + JSON.toJSONString(params) + ")";

        //ObjectMapper.writeValueAsString()是jackson-core的
        ObjectMapper om = new ObjectMapper();
        try {
            return target.getClass().getSimpleName() + "#" + method.getName()
                + "(" + om.writeValueAsString(params) + ")";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
