package com.huangjiabin.cache.service.impl;

import com.huangjiabin.cache.entity.User;
import com.huangjiabin.cache.service.EhcacheService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class EhcacheServiceImpl implements EhcacheService {

    /*
    @Cacheable表示可以缓存，第一次调用该方法返回结果会被缓存下来
        参数 value：缓存哪个cache
        参数 key：   缓存数据的key值，默认不写则为方法传进来的参数
        参数 condition；满足条件才走缓存
        结果，”userInfo“:new User("1001","徐凤年","123") 会存进UserCache中
    */
    @Cacheable(
//            value = "UserCache",key = "userInfo"
            value = "UserCache",key = "'user:'+#id"    //如果参数为1001则最后生成的key：”user:1001“
//            value = "UserCache",keyGenerator = "selfKeyGenerate"
//            ,condition = "#id.length()<5"
    )
    @Override
    public User findById(String id) {
        System.out.println("execute findById....");
        return new User(id,"徐凤年","123");
    }
}
