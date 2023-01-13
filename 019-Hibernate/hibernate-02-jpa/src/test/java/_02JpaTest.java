import com.huangjiabin.po.Customer;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class _02JpaTest {
    private EntityManagerFactory factory;
    @Before
    public void before(){
        //通过切换下面的名字来切换jpa
        factory = Persistence.createEntityManagerFactory("hibernateJPA");

    }

    //"insert"——>persist ：添加数据
    @Test
    public void test_persist() {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = new Customer();
        customer.setCustName("张三");
        entityManager.persist(customer);

        transaction.commit();
   }
    //"select" ——>find ：立即查询
    @Test
    public void test_find(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Customer customer = em.find(Customer.class, 1L);
        System.out.println("========================");
        System.out.println(customer);

        tx.commit();
    }
    //"select" ——>getReference ：延迟查询，没有get哈
    @Test
    public void test_getReference(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Customer customer = em.getReference(Customer.class, 1L);
        System.out.println("========================");
        System.out.println(customer);

        tx.commit();
    }

    //"update" ——>merge ：先select，再判断更新数据和数据库中数据是否一致，不一致则update，如果查询不到会insert
    @Test
    public void test_marge(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();


        Customer customer = new Customer();
        customer.setCustId(5L);
        customer.setCustName("徐凤年");
        em.merge(customer);

        tx.commit();
    }
    //"delete" ——>remove ：
    // 不能删除游离态的数据，
    // remove-commit之间的状态为删除状态。remove后不会马上删除，会变成删除状态，commit后才删除，
    // 删除状态下再persist的话，就会抵消，没有delete sql也没有insert sql
    @Test
    public void test_remove(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        /*报错，不能删除游离态的数据
        Customer customer = new Customer();
        customer.setCustId(10L);
        em.remove(customer);
        */
        Customer customer = em.find(Customer.class, 10L);
        em.remove(customer);

        tx.commit();
    }

    //"jpql" ——>直接操作sql语句
    @Test
    public void test_jpql(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        String jpql = "UPDATE Customer set custName = :name where custId = :id";
        em.createQuery(jpql)
                .setParameter("name","王二")
                .setParameter("id",99L)
                .executeUpdate();
        /*第二种传参方式
        String jpql = "UPDATE Customer set custName = ?1 where custId = ?2";
        em.createQuery(jpql)
                .setParameter(1,"王二")
                .setParameter(2,5L)
                .executeUpdate();
        */
        /*第三种传参，这种只能用在@Query注解上
            @Query(value="from Customer  c where c.custId=:#{#customer.custId}")
            Customer findCustomerByInfo(@Param("customer") Customer customer);

        */
        /*
        org.hibernate.QueryException：不再支持旧式查询参数（`?`）；改用 JPA 风格的序数参数（例如，`?1`）：
        String jpql = "UPDATE Customer set custName = ? where custId = ?";
        em.createQuery(jpql)
                .setParameter(0,"王二")
                .setParameter(1,5L)
                .executeUpdate();
        */

        tx.commit();
    }
    //"sql" ——>直接操作sql语句
    @Test
    public void test_sql(){
        EntityManager em = factory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        String sql = "UPDATE tb_customer set cust_name = :name where id = :id";
        em.createNativeQuery(sql)
                .setParameter("name","李四")
                .setParameter("id",5L)
                .executeUpdate();

        tx.commit();
    }
    //jpa的一级缓存是作用在em对象上的，同一个EntityManager对象，查询同一个sql只会查一次
}
