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
            value = "UserCache",key = "'user:'+#id",     //如果参数为1001则最后生成的key：”user:1001“
//            value = "UserCache",keyGenerator = "selfKeyGenerate"
            condition = "#id.length()<5"
    )
    @Override
    public User findById(String id) {
        System.out.println("execute findById....");
        return new User("1001","徐凤年","123");
    }

    /*
    @CachePut 不仅会缓存方法的返回结果，还会执行方法的代码段，相当于每次都会调用数据库，都会覆盖缓存

    */
    @CachePut(value = "UserCache")
    @Override
    public String refreshByName(String username) {
        System.out.println("模拟从数据库加载数据:"+Math.round(Math.random()*100000));
        return username+"::"+Math.round(Math.random()*100000);
    }

    /*
     @CacheEvict 与@Cacheable相反，表示删除或失效无用的缓存数据
        参数 allEntries 为true表示清楚缓存中全部数据，就算传入的参数未匹配，默认为false
    */
    @CacheEvict(
        value="UserCache"
//        ,allEntries = true
    )
    @Override
    public void removeById(String id) {
        System.out.println("模拟从数据库删除数据...");
    }
}
