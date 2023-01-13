
import com.huangjiabin.po.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class _01HibernateTest {

    // Session工厂  Session:数据库会话  代码和数据库的一个桥梁
    private SessionFactory sf;

    @Before
    public void init() {
        /* 1、简单配置：配置的xml命名必须是hibernate.cfg.xml
        Configuration cfg = new Configuration();
        cfg.configure();
        sf = cfg.buildSessionFactory();
        */

        //2、自定义配置
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("/hibernate.cfg.xml").build();
        //根据服务注册类创建一个元数据资源集，同时构建元数据并生成应用一般唯一的的session工厂
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    // "insert" ——>save：添加
    @Test
    public void testSave(){
        // session进行持久化操作
        try(Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            Customer customer = new Customer();
            customer.setCustName("徐凤年");

            session.save(customer);

            tx.commit();
        }

    }


    // "select" ——> find ：立即查询
    @Test
    public void testFind(){
        // session进行持久化操作
        try(Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            /*
            1、这里的find底层调用的是get 并没有传LockModeType值  1L会从Object转为Serializable查主键,
               是我理解复杂了，其实就是通过id查数据。
            2、为什么是1L,因为数据库类型是bigint
            */
            Customer customer = session.find(Customer.class, 1L);
            System.out.println("=====================");
            System.out.println(customer);

            tx.commit();
        }

    }


    // "select" ——>load ：延迟查询
    @Test
    public void testLoad(){
        // session进行持久化操作
        try(Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            Customer customer = session.load(Customer.class, 1L);
            System.out.println("=====================");
            System.out.println(customer);

            tx.commit();
        }

    }


    // "update" ——>saveOrUpdate ：有id就update，没id就insert
    @Test
    public void testSaveOrUpdate(){
        // session进行持久化操作
        try(Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            Customer customer = new Customer();
            customer.setCustId(10L);
            customer.setCustAddress("aaa");
            customer.setCustName("徐庶222");

            session.saveOrUpdate(customer);
            tx.commit();
        }

    }


    // "delete" ——>remove ：删除 ，没有id会抛javax.persistence.OptimisticLockException异常，没有传id会没有反应
    @Test
    public void testD(){
        // session进行持久化操作
        try(Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            Customer customer = new Customer();
            customer.setCustId(11L);
            session.remove(customer);

            tx.commit();
        }
    }


    //  hql 查询
    @Test
    public void testHQL(){
        // session进行持久化操作
        try(Session session = sf.openSession()){
            Transaction tx = session.beginTransaction();

            String hql=" FROM Customer where custId=:id";

            List<Customer> resultList = session.createQuery(hql, Customer.class)
                    .setParameter("id",1L)
                    .getResultList();

            tx.commit();
        }
    }
}
