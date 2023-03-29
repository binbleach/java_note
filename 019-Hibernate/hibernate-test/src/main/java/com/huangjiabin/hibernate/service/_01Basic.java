package com.huangjiabin.hibernate.service;

import com.huangjiabin.hibernate.po.Customer;
import com.huangjiabin.hibernate.utils.HibernateUtils;
import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/*
    内容讲解：
        1、测试两个同名类在hibernate中创建对象的效果
    总结：
        1、不设置entity-name：默认为全限定类名。（有不覆盖，先配置生效）
            例：com.huangjiabin.hibernate.po.Customer -> entity
        2、imports：
            1）会根据entity-name名生成映射。
                例：com.huangjiabin.hibernate.po.Customer - >com.huangjiabin.hibernate.po.Customer
            2）如果设置 auto-import="true"（默认）则会根据entity-name的类名生成一个映射（有则覆盖，后配置的生效）
               例：Customer -> com.huangjiabin.hibernate.po.Customer
               注：如果是注释形式，设置了entity-name属性，则 auto-import="false"
        3、查找：
            1）Customer.class方式寻找需要那entity-name 必须是 有com.huangjiabin.hibernate.po.Customer
            2）com.huangjiabin.hibernate.po.Customer 方式去寻找 entityPersisters有该类型对象就可以了
        4、高版本相同类必须有一个设置entity-name，且不能为类名（可以两个都设置为类名）


*/

public class _01Basic {
    // SessionFactory：生成Session的工厂，构造它很消耗资源，一般情况下一个应用中只初始化一个 SessionFactory 对象。
    private SessionFactory sessionFactory;
    // Session:应用程序和数据库的会话，用于crud
    private Session session;
    // 4 开启事务
    private Transaction transaction;
    @Before
    public void init() {
         //第1种创建sessionFactory方式：（配置文件命名必须是hibernate.cfg.xml）
        Configuration cfg = new Configuration();    //加载hibernate 核心配置文件
        cfg.configure();
        sessionFactory = cfg.buildSessionFactory(); //创建SessionFactory对象

        //第2种创建sessionFactory方式：
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();//根据服务注册类创建一个元数据资源集，同时构建元数据并生成应用一般唯一的session工厂

        //使用SessionFactory创建session对象
        session = sessionFactory.openSession();
        //开启事务
        transaction = session.beginTransaction();//开启事务
    }
    @After
    public void close() {
        //6 提交事务
        if(TransactionStatus.COMMITTED.isNotOneOf(transaction.getStatus())){
            transaction.commit();
        }
        //关闭资源
        if(session.isConnected()){
            session.close();
            sessionFactory.close();
        }
    }

    // find ：立即查询
    @Test
    public void test_find(){
        /*
            1、这里的find底层调用的是get 并没有传LockModeType值  1L会从Object转为Serializable查主键,
               是我理解复杂了，其实就是通过id查数据。
            2、为什么是1L,因为数据库类型是bigint
        */
        Customer customer = session.get(Customer.class, 1);
        System.out.println("=====================");
        System.out.println(customer);


//        System.out.println("=============query=============");
//        Query query = session.createQuery("from com.huangjiabin.hibernate.po.Customer");
//        List<Customer> list = query.list();
//        for(Customer user:list){
//            System.out.println(user);
//        }

    }



}
