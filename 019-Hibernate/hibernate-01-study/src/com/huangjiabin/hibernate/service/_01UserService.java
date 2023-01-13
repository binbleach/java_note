package com.huangjiabin.hibernate.service;

import com.huangjiabin.hibernate.model.User;
import com.huangjiabin.hibernate.utils.HibernateUtils;
import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class _01UserService {
    //session.save()添加数据，可以不设置id,设置了也没用（如果设置了自增策略的话）
    @Test
    public void test_save(){
        // 1 加载hibernate 核心配置文件
        Configuration cfg = new Configuration();
        cfg.configure();
        // 2 创建SessionFactory对象
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        // 3 使用SessionFactory创建session对象
        Session session = sessionFactory.openSession();
        // 4 开启事务
        Transaction transaction = session.beginTransaction();//开启事务
        // 5 crud
        User user = new User();
        user.setId(2); //就算设置id也无用因为主键自增
        user.setUsername("徐凤年");
        user.setPassword("123");
        user.setAddress("北凉");
        session.save(user);
        //6 提交事务
        transaction.commit();
        //关闭资源
        session.close();
        sessionFactory.close();
    }
    @Test
    public void test_saveForUtils(){
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        // 3 使用SessionFactory创建session对象
        Session session = sessionFactory.openSession();
        // 4 开启事务
        Transaction transaction = session.beginTransaction();//开启事务
        // 5 crud
        User user = new User();
        user.setUsername("番薯");
        user.setPassword("123");
        user.setAddress("土");
        session.save(user);
        //6 提交事务
        transaction.commit();
        //关闭资源
        session.close();
        //利用工具类获取sessionFactory，是单例模式所以不需要关闭
        //sessionFactory.close();
    }
    @Test
    public void test_get(){
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // 5 crud
        //第一个参数是实体类class,第二个是id值
        User user = session.get(User.class, 1);
        System.out.println("==============");
        System.out.println(user);
        transaction.commit();
        session.close();
    }
    @Test
    public void test_load(){
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        /*
        第一个参数是实体类class,第二个是id值
        load属性是，找不到值则报错org.hibernate.ObjectNotFoundException，
        load是延迟加载的，会先输出====，等输出user的时候才回去查询sql.这里和get相反
        */
        User user = session.load(User.class, 1);
        System.out.println("===-");
        System.out.println(user);

        transaction.commit();
        session.close();
    }

    //session.update()根据id修改值，若属性为空则对应修改为空
    @Test
    public void test_update(){
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();//开启事务

        // 5 crud
        //update若属性为空则字段也会改为空
        User user = new User();

        //正常情况
        /*user.setId(2);
        user.setPassword("1");*/

        //id不存在情况：报错Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1
        /*user.setId(2);
        user.setPassword("3");*/


        //通过get获取对象
        user=session.get(User.class, 2);
        //持久态不允许修改id：报错identifier of an instance of com.huangjiabin.hibernate.model.User was altered from 2 to 999
        //user.setId(3);
        user.setPassword("8");

        session.update(user);

        //有趣的顺序，如果方法放在get和delete中间，会先selsect->update->delete
        Query query = session.createQuery("delete User u where u.id = ?");
        query.setParameter(0,999);
        query.executeUpdate();


        transaction.commit();
        session.close();
    }

    //session.delete()根据id删除
    @Test
    public void delete(){
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        //delete
        /*  //根据id删除值，不一定要get,可以如下直接创建对象设置属性删除
            User user = session.get(User.class, 2);
        */
        User user = new User();
        //id不存在会报错Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1
        //user.setId(100);
        user.setId(6);
        session.delete(user);   //sql：delete from user where id=?

        transaction.commit();
        session.close();
    }
    @Test
    public void saveOrUpdate(){
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // 5 crud
        User user = session.get(User.class, 9);
        if(user==null){
            user = new User();
            user.setId(2);
            user.setUsername("番薯");
            user.setPassword("234");
            user.setAddress("嘞");
        }
        session.saveOrUpdate(user);

        transaction.commit();
        session.close();
    }

    //验证一级缓存，就是查询一条数据两次，查看效果。可以debugger查看控制台输出，是否执行sql语句
    @Test
    public void validateL1Cache(){
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // 5 crud
        User user = session.get(User.class, 3);
        System.out.println(user);
        User user2 = session.get(User.class, 3);
        System.out.println(user2);
        System.out.println(user==user2);

        transaction.commit();
        session.close();
    }

    /*
        hibernate其他查询api
    */
    @Test
    public void hibernateApi(){
        Session session;
        Transaction transaction=null;
        try{
            session = HibernateUtils.getSessionObject();
//            session=HibernateUtils.getSessionFactory().openSession();
             transaction = session.beginTransaction();

            // 1、query：不需要写sql语句，需要写hql语句，注意User首字母大写，是映射的类名
            System.out.println("=============query=============");
            Query query = session.createQuery("from User");
            List<User> list = query.list();
            for(User user:list){
                System.out.println(user);
            }

            // 2、criteria
            System.out.println("=============criteria=============");
            Criteria criteria = session.createCriteria(User.class);
            List<User> list1 = criteria.list();
            for(User user:list1){
                System.out.println(user);
            }

            // 3、sqlQuery（写普通sql语句）：默认返回每部分不是对象，是数组
            System.out.println("=============sqlQuery=============");
            SQLQuery sqlQuery = session.createSQLQuery("select * from user");
            //默认返回每部分不是对象，是数组
            List<Object[]> list2 = sqlQuery.list();
            for(Object[] user :list2){
                System.out.println(Arrays.toString(user));
            }
            System.out.println("=============sqlQuery数组转对象=============");
            sqlQuery = sqlQuery.addEntity(User.class);
            List<User> list3=sqlQuery.list();
            for(User user:list3){
                System.out.println(user);
            }

            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            //本地线程绑定session，无需手动关闭
            //session.close();
            //单例模式无需关闭sessionFactory
            //sessionFactory.close();
        }
    }

    //验证jpa
    @Test
    public void aaa(){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("/hibernate.cfg.xml").build();
        //根据服务注册类创建一个元数据资源集，同时构建元数据并生成应用一般唯一的的session工厂
        SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sf.openSession();
//        session.find()

    }

    //验证jpa
    @Test
    public void bbb(){
        Session session = HibernateUtils.getSessionObject();
        Transaction transaction = session.beginTransaction();


        //利用hql更新值时没有id也不会报错
        /*Query query = session.createQuery("update User u set u.password=? where u.id = ?");
        query.setParameter(0,"1");
        query.setParameter(1,999);
        query.executeUpdate();*/

        //利用hql删除值时没有id也不会报错
        Query query = session.createQuery("delete User u where u.id = ?");
        query.setParameter(0,999);
        int i = query.executeUpdate();
        System.out.println(i);

        transaction.commit();

    }



}
