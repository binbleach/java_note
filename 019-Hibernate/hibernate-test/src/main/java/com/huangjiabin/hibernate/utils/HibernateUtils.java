package com.huangjiabin.hibernate.utils;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    private final static Configuration cfg;
    private final static SessionFactory sessionFactory;
    static{
        cfg = new Configuration();
        cfg.configure();
        sessionFactory = cfg.buildSessionFactory();
    }
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    public static Session getSessionObject(){
        return sessionFactory.getCurrentSession();
    }
}
