package com.huangjiabin.hibernate.service;

import com.huangjiabin.hibernate.model.Authority;
import com.huangjiabin.hibernate.model.Customer;
import com.huangjiabin.hibernate.model.LinkMan;
import com.huangjiabin.hibernate.model.Role;
import com.huangjiabin.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

public class _03HibernateManyToMany {

    //多对多级联保存
    @Test
    public void manyToManySave(){
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        try {
            sessionFactory = HibernateUtils.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();

            //创建三个对象分别添加
            Authority authority  =new Authority();
            authority.setName("selectUserList");
            authority.setRemark("查看用户列表");
            Role role = new Role();
            role.setName("admin");
            role.setRemark("管理员");
            role.getAuthoritySet().add(authority);
            session.save(role);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
    }
    //多对多级联删除,业务上多对多不会级联删除
    @Test
    public void manyToManyDelete(){
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        try {
            sessionFactory = HibernateUtils.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();

            //缓存特性持久态的数据将会被提交修改，无需session的操作
            Role role = session.get(Role.class,3);
            session.delete(role);


            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
    }
    //维护关系表从而维护角色和权限的关系
    @Test
    public void manageRoleAuthority(){
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        try {
            sessionFactory = HibernateUtils.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();

            // 1、使管理员拥有删除用户角色
            /*
            Authority authority = new Authority();
            authority.setName("deleteUserById");
            authority.setRemark("通过id删除用户");
            Role role = session.get(Role.class,4);
            role.getAuthoritySet().add(authority);
            session.save(role);
            */
            // 2、让管理员失去删除用户角色
            Role role = session.get(Role.class,4);
            Authority authority = session.get(Authority.class,4);
            role.getAuthoritySet().remove(authority);

            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
    }
}
