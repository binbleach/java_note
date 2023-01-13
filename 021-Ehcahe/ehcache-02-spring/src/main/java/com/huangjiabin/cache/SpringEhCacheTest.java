package com.huangjiabin.cache;

/**
 * Hello world!
 *
 */

import com.huangjiabin.cache.entity.User;
import com.huangjiabin.cache.service.EhcacheService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml","classpath:spring/spring-ehcache.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringEhCacheTest {
    @Autowired
    EhcacheService ehcacheService;

    @Autowired
    CacheManager cacheManager;

    //测试ehcacheService存入缓存，调用两次findById方法，模拟调用两次查找数据库方法，结果是只执行一次findById方法
    @Test
    public void testFindById() {
        User user1 = ehcacheService.findById("1001");
        System.out.println(user1);
        System.out.println("------------------");
        User user2 = ehcacheService.findById("1001");
        System.out.println(user2);
    }
    //测试用cacheManager存入缓存
    @Test
    public void testFindById2() {
        //调用一次相当于存储一次
        ehcacheService.findById("1001");
        Cache userCache = cacheManager.getCache("UserCache");
        Element element = userCache.get("user:1001");
        User user = (User)element.getObjectValue();
        System.out.println(user);


    }
    //测试刷新缓存
    @Test
    public void testCachePut() {
        String username1 = ehcacheService.refreshByName("徐凤年");
        System.out.println(username1);
        System.out.println("================");
        String username2 = ehcacheService.refreshByName("徐凤年");
        System.out.println(username2);
        System.out.println("================");
        String username3 = ehcacheService.refreshByName("徐凤年");
        System.out.println(username3);

    }
    //测试删除缓存
    @Test
    public void testCacheEvict() {
        //存入缓存
        ehcacheService.findById("1001");
        System.out.println(cacheManager.getCache("UserCache").getKeys());
        //删除缓存
        ehcacheService.removeById("user:1001");
        System.out.println(cacheManager.getCache("UserCache").getKeys());

    }
}
