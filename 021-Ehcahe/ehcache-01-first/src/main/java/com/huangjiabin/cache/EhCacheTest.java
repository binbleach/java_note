package com.huangjiabin.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * EhCache测试类
 *
 */
public class EhCacheTest {
    public static void main( String[] args )
    {
        //1、获取CacheManager，默认加载ehcache,也可以输入指定文件路径去加载
        CacheManager cacheManager = CacheManager.create("31Cache/cache-01-ehcahce/src/main/resources/ehcache.xml");
//        CacheManager cacheManager = CacheManager.create();
        //2、获取cache实例
        Cache helloWorldCache = cacheManager.getCache("HelloWorldCache");
        //3、存入一个元素
        Element element = new Element("key1", "value1");
        helloWorldCache.put(element);

        //4、取出元素
        Element value = helloWorldCache.get("key1");
        System.out.println(value);

        //5、取出元素值，也可以取出键，有很多种操作，这里元素存的是什么值，取出来的时候就可以转为什么值，可以存储对象
        String objectValue = (String)value.getObjectValue();
        System.out.println(objectValue);

        //拓展
        System.out.println("缓存剩余元素个数："+helloWorldCache.getSize());
        helloWorldCache.remove("key1");
        System.out.println("缓存剩余元素个数："+helloWorldCache.getSize());

        //6、关闭CacheManager
        cacheManager.shutdown();
    }
}
