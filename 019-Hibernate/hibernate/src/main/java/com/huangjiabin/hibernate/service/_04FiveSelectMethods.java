package com.huangjiabin.hibernate.service;

import com.huangjiabin.hibernate.model.Authority;
import com.huangjiabin.hibernate.model.Role;
import com.huangjiabin.hibernate.utils.HibernateUtils;
import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

//hibernate的五种查询方式
public class _04FiveSelectMethods {

    /*
    *   methods：对象导航查询
    *   describe：根据id查找某个角色和角色下所有权限
    */
    @Test
    public void dxdhSelect(){
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        try{
            sessionFactory = HibernateUtils.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            transaction.begin();

            //查询管理员和管理员所有的权限
            Role role = session.get(Role.class, 4);
            Set<Authority> authoritySet = role.getAuthoritySet();
            for(Authority authority:authoritySet){
                System.out.println("=============================");
                System.out.println(authority);
            }
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            session.close();
        }
    }

    /*
    *   methods：OID查询
    *   describe：根据id查询某条记录返回对象
    *   所谓的oid查询就是：Role role = session.get(Role.class, 4);
    */

    /*
     *   methods：hql查询（UserService中用过）
     *   describe：hql操作的的使实体类属性，sql操作的使表的字段
     *   使用：1、创建Query对象，写hql语句；2、调用query对象里的方法得到结果
     */
    @Test
    public void hqlSelect(){
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        try{
            sessionFactory = HibernateUtils.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            transaction.begin();

            // 1、查询所有且排序
            /*
                Query query = session.createQuery("from Role order by id desc");
                List<Role> list = query.list();
                for(Role role:list){
                    System.out.println(role.getId());
                }
            */

            // 2、条件查询
            /*
                Query query = session.createQuery("from Role where name like ? and id=? ");
                query.setParameter(0,"%admin%");
                query.setParameter(1,4);
                List<Role> list = query.list();
                for(Authority authority:list.get(0).getAuthoritySet()){
                    System.out.println(authority.getRemark());
                }
            */

            //分页查询（hql语句不能用limit，用query对象封装的两个方法实现）
            /*
                Query query = session.createQuery("from Role");
                query.setFirstResult(0); //设置limit(?,?)
                query.setMaxResults(2);
                List<Role> list = query.list();
                for(Role role:list){
                    System.out.println(role.getId());
                }
            */

            //投影查询（就是不用 select *(hql语句也不支持 select *) 而是用 select+某个字段）
            /*
                Query query = session.createQuery("select id from Role");
                List<Integer> list = query.list();
                for(Integer id : list){
                    System.out.println(id);
                }
            */

            //聚合函数查询（count、sum、arg、max、min）
            /*
                Query query = session.createQuery("select count(*) from Role");
                Object obj = query.uniqueResult();  //可直接返回唯一结果,这个结果不能直接转为int,得先转Long,再转int
                System.out.println(obj);
            */

            //内连接
            /*
                Query query = session.createQuery("from Customer c inner join c.setLinkMan");
                List<Object[]> list = query.list();    //返回的是obj对象
                for(Object[] user :list){
                    System.out.println(Arrays.toString(user));
                }
            */

            //迫切内连接（与内连接区别是返回得是对象，不是数组对象）
            /*
                Query query = session.createQuery("from Customer c inner join fetch c.setLinkMan");
                List<Object> list = query.list();    //返回的是obj对象
                for(Object user :list){
                    System.out.println(user);
                }
            */

            //左外连接
            /*
                Query query = session.createQuery("from Customer c left join c.setLinkMan");
                List<Object[]> list = query.list();    //返回的是obj对象
                for(Object[] user :list){
                    System.out.println(Arrays.toString(user));
                }
            */

            //迫切左外连接（与左外连接的区别是返回的结果是对象，不是数组）
            /*
                Query query = session.createQuery("from Customer c left join fetch c.setLinkMan");
                List<Object> list = query.list();    //返回的是obj对象
                for(Object user :list){
                    System.out.println(user);
                }
            */

            //右外连接
            Query query = session.createQuery("from Customer c right join c.setLinkMan");
            List<Object[]> list = query.list();    //返回的是obj对象
            for(Object[] user :list){
                System.out.println(Arrays.toString(user));
            }





            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            session.close();
        }
    }

    /*
     *   methods：QBC查询（UserService中用过）
     *   describe：Criteria对象
     */
    @Test
    public void qbcSelect(){
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        try{
            sessionFactory = HibernateUtils.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            transaction.begin();

            //查询所有
           /*
                Criteria criteria = session.createCriteria(Role.class);
                List<Role> list = criteria.list();
                for(Role role : list){
                    System.out.println(role.getId() +" : "+role.getRemark());
                }
            */

            //条件查询（Restrictions类的方法与mybatis plus类似）
            /*
                Criteria criteria = session.createCriteria(Role.class);
                Criteria id = criteria.add(Restrictions.eq("id", 4));
                Role o = (Role) id.uniqueResult();
                System.out.println(o.getId()+" : "+o.getRemark());
            */

            //排序查询
            /*
                Criteria criteria = session.createCriteria(Role.class);
                criteria.addOrder(Order.desc("id"));
                List<Role> list = criteria.list();
                for(Role role : list){
                    System.out.println(role.getId() +" : "+role.getRemark());
                }
             */

            //分页查询
            /*
                Criteria criteria = session.createCriteria(Role.class);
                criteria.setFirstResult(0);
                criteria.setMaxResults(2);
                List<Role> list = criteria.list();
                for(Role role : list){
                    System.out.println(role.getId() +" : "+role.getRemark());
                }
            */

            //统计查询
            /*
                Criteria criteria = session.createCriteria(Role.class);
                criteria.setProjection(Projections.rowCount());
                Object obj = criteria.uniqueResult();
                //obj不能直接转成int，得先转成Long，再转成int
                Long totalLong = (Long)obj;
                int total = totalLong.intValue();
                System.out.println(total);
            */

            //离线查询不用session创建对象（业务场景：可以分层，在上层设置条件，下层直接查询）
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
            detachedCriteria.addOrder(Order.desc("id"));
            Criteria criteria = detachedCriteria.getExecutableCriteria(session);
            List<Role> list = criteria.list();
            for(Role role : list){
                System.out.println(role.getId() +" : "+role.getRemark());
            }

            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }finally {
            session.close();
        }
    }

    /*
     *   methods：本地sql查询
     *   describe：用普通sql查询
     *   方法    ：session.createSQLQuery("select * from user");
     */

}
