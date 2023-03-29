package com.huangjiabin.hibernate.service;

import com.huangjiabin.hibernate.model.User;
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

/*test_find
    一、两个类名相同下
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
    二、hibernate session方法（有一些是新版本特有入：saveOrUpdate不用特意去记它）：
        flush()、evict()、load()、save()、saveOrUpdate()、update()、merge()、persist()、delete()
        refresh()、get()
    三、jpa EntityManager的方法（不通过jpa调用，实际调用效果上还是有些差异的，如remove可删除非持久态）：
        persist()、merge()、remove()、find()、flush()、refresh()、detach()、getReference()

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

    // save：添加数据，可以不设置id（如果设置了自增策略的话,设置了也没用）
    @Test
    public void test_save(){
        // 5 crud
        User user = new User();
        user.setId(2); //就算设置id也无用因为主键自增
        user.setUsername("徐凤年");
        user.setPassword("123");
        user.setAddress("北凉");
        session.save(user);

    }
    @Test
    public void test_saveForUtils(){
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        // 3 使用SessionFactory创建session对象
        Session session = sessionFactory.getCurrentSession();
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
        //本地线程绑定session，无需手动关闭
        //session.close();
        //利用工具类获取sessionFactory，是单例模式所以不需要关闭
        //sessionFactory.close();
    }
    // get
    @Test
    public void test_get() {

        // 5 crud
        //第一个参数是实体类class,第二个是id值
        User user = session.get(User.class, 1);
        System.out.println("==============");
        System.out.println(user);
    }
    // find：立即查询。EntityManager(jpa)的接口
    @Test
    public void test_find(){
        /*
            1、这里的find底层调用的是get 并没有传LockModeType值  1L会从Object转为Serializable查主键,
               是我理解复杂了，其实就是通过id查数据。
            2、为什么是1L,因为数据库类型是bigint
        */
        Customer customer = session.find(Customer.class, 1L);
        System.out.println("=====================");
        System.out.println(customer);

    }
    // load：延迟查询
    @Test
    public void test_load(){
        /*
        第一个参数是实体类class,第二个是id值
        load属性是，找不到值则报错org.hibernate.ObjectNotFoundException，
        load是延迟加载的，会先输出====，等输出user的时候才回去查询sql.这里和get相反
        */
        User user = session.load(User.class, 1);
        System.out.println("=========");
        System.out.println(user);

    }

    // update：根据id修改值，若属性为空则对应修改为空
    @Test
    public void test_update(){

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

    }
    //saveOrUpdate ：有id就update，没id就insert
    @Test
    public void saveOrUpdate(){

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

    }

    // delete：根据id删除
    @Test
    public void delete(){

        //delete
        /*  //根据id删除值，不一定要get,可以如下直接创建对象设置属性删除
            User user = session.get(User.class, 2);
        */
        User user = new User();
        //id不存在会报错Batch update returned unexpected row count from update [0]; actual row count: 0; expected: 1
        user.setId(26);
//        user.setId(7);
        session.delete(user);   //sql：delete from user where id=?
    }

    // remove ：根据id删除。EntityManager(jpa)的接口。
    // 不能删除游离态的数据，这里可以可能它不是纯正的jpa
    @Test
    public void remove(){
        Customer customer = new Customer();
        customer.setCustId(17L);     //没有id会抛javax.persistence.OptimisticLockException异常，没有传id会没有反应
        session.remove(customer);
    }

    //验证一级缓存，就是查询一条数据两次，查看效果。可以debugger查看控制台输出，是否执行sql语句
    @Test
    public void validateL1Cache(){

        // 5 crud
        User user = session.get(User.class, 3);
        System.out.println(user);
        User user2 = session.get(User.class, 3);
        System.out.println(user2);
        System.out.println(user==user2);

    }

    /*
        hibernate其他查询api
    */
    @Test
    public void hibernateApi(){
        try{

            // 1、query：不需要写sql语句，需要写hql语句，注意User首字母大写，是映射的类名
            System.out.println("=============query-select=============");
            Query query = session.createQuery("from User");
            List<User> list = query.list();
            for(User user:list){
                System.out.println(user);
            }

            //query-update，旧版本，新版本不支持 ?形式的参数
            System.out.println("=============query-update=============");
//            Query query2 = session.createQuery("update User u set u.password=? where u.id = ?");
//            query2.setParameter(0,"1");
//            query2.setParameter(1,3);
//            query2.executeUpdate();
            Query query2 = session.createQuery("update User u set u.password=:password where u.id = :id");
            query2.setParameter("password","123");
            query2.setParameter("id",3);
            query2.executeUpdate();

            //query-delete，旧版本，新版本不支持 ?形式的参数
            System.out.println("=============query-delete=============");
//            Query query3 = session.createQuery("delete User u where u.id = ?");
//            query3.setParameter(0,2);
//            int i = query3.executeUpdate();
//            System.out.println(i);
            Query query3 = session.createQuery("delete User u where u.id = :id");
            query3.setParameter("id",2);
            int i = query3.executeUpdate();
            System.out.println(i);

            //query-新版本，session特有接口
            System.out.println("=============query-新版本=============");
            List<Customer> resultList = session.createQuery("from com.huangjiabin.hibernate.po.Customer where custId=:id", Customer.class)
                    .setParameter("id",1L)
                    .getResultList();


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
            session.close();
            sessionFactory.close();
        }
    }



}
