package com.huangjiabin.hibernate.service;

import com.huangjiabin.hibernate.model.Customer;
import com.huangjiabin.hibernate.model.LinkMan;
import com.huangjiabin.hibernate.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

public class _02HibernateOneToMany {

    //级联保存，简便方式
    @Test
    public void oneToManySave(){
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        try {
            sessionFactory = HibernateUtils.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();

            LinkMan linkMan = new LinkMan();
            linkMan.setLkm_name("马化腾");
            linkMan.setLkm_gender("男");
            linkMan.setLkm_phone("18889599328");
            Customer customer = new Customer();
            customer.setCustName("腾讯");
            customer.setCustLevel("一级客户");
            customer.setCustSource("广告");
            customer.getSetLinkMan().add(linkMan);
            session.save(customer);
            System.out.println(customer);

            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
    }
    //级联修改
    @Test
    public void oneToManyDelete(){
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        try {
            sessionFactory = HibernateUtils.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();

            //缓存特性持久态的数据将会被提交修改，无需session的操作
            Customer customer = session.get(Customer.class,9);
            LinkMan linkMan = session.get(LinkMan.class,8);
            customer.getSetLinkMan().add(linkMan);
            linkMan.setCustomer(customer);


            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
    }
    //级联删除
    @Test
    public void oneToManydelete(){
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        try {
            sessionFactory = HibernateUtils.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.getTransaction();
            transaction.begin();

            Customer customer = session.get(Customer.class, 2);
            session.delete(customer);

            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
    }

}
