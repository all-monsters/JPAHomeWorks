package cn.com.taiji.domain;

/**
 * @program: jpa2
 * @description: test
 * @author: liu yan
 * @create: 2019-07-29 16:32
 */

import org.hibernate.Criteria;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        // 1. 创建EntityManagerFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");

        // 2. 创建EntityManager
        EntityManager entityManager = factory.createEntityManager();

        /*//插入数据，添加人和地址
        add(entityManager);*/

        /*//删除数据，删除人和地址，级联
        del(entityManager);*/

       /* //查询全部地址(原生sql）
        selectAllAddress(entityManager);*/

        /*//根据地址名称查询
        selectByAddressName(entityManager);*/

        /*//查询全部地址（根据注解namedquery）
        selectAllAddressByNamedQuery(entityManager);*/

        /*//添加授权与用户（many to many）
        addAuthorityAndUser(entityManager);*/

        //查询书籍信息（criteria查询方式）
        selectBookByName(entityManager);

        /*//动态查询book
        findEmployees(entityManager);*/

        // 6. 关闭EntityManager
        entityManager.close();

        // 7. 关闭EntityManagerFactory
        factory.close();
    }

    //动态查询book
    public static List<Book> findEmployees(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        String bookname="aa";
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> c = cb.createQuery(Book.class);
        Root<Book> bookRoot = c.from(Book.class);
        c.select(bookRoot);
        c.distinct(true);
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (bookname != null) {
            ParameterExpression<String> p =
                    cb.parameter(String.class, "bookname");
            criteria.add(cb.equal(bookRoot.get("bookname"), p));
        }
        if (criteria.size() == 0) {
            throw new RuntimeException("no criteria");
        } else if (criteria.size() == 1) {
            c.where(criteria.get(0));
        } else {
            c.where(cb.and(criteria.toArray(new Predicate[0])));
        }
        TypedQuery<Book> q = entityManager.createQuery(c);
        if (bookname != null) { q.setParameter("bookname", bookname); }
        //System.out.println(q.getResultList());

        // 5. 提交事务
        transaction.commit();

        return q.getResultList();
    }


    //查询书籍信息（criteria查询方式）
    private static void selectBookByName(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> c=cb.createQuery(Book.class);
        Root<Book> root=c.from(Book.class);
        c.select(root).where(cb.in(root.get("bookname")).value("aa").value("a"));
        TypedQuery query=entityManager.createQuery(c);
        List<Book> list=query.getResultList();
        System.out.println(list);
        // 5. 提交事务
        transaction.commit();
    }

    //添加授权与用户（many to many）
    private static void addAuthorityAndUser(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        User user=new User();
        user.setUsername("Tom");
        user.setPassword("123456");

        Authority authority=new Authority();
        authority.setName("Jerry");

        List<Authority> authorities=new ArrayList<>();
        authorities.add(authority);
        user.setAuthorityList(authorities);

        entityManager.persist(user);
        entityManager.persist(authority);

        // 5. 提交事务
        transaction.commit();
    }

    //查询全部地址（根据注解namedquery）
    private static void selectAllAddressByNamedQuery(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作查询
        List<Address> list= entityManager.createNamedQuery("FindAll").getResultList();
        System.out.println("根据注解namedquery"+list.toString());
        // 添加user到数据库，相当于hibernate的save();
        //entityManager.persist(list);
        // 5. 提交事务
        transaction.commit();
    }

    //通过地址名字查询
    private static void selectByAddressName(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作查询
        Address address= (Address) entityManager.createNativeQuery("select * from Address where address=?1",Address.class).setParameter(1,"呼和浩特").getSingleResult();
        System.out.println(address.toString());
        // 添加user到数据库，相当于hibernate的save();
        entityManager.persist(address);
        // 5. 提交事务
        transaction.commit();
    }

    //查询全部地址数据(常规）
    private static void selectAllAddress(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        List<Address> addresses=entityManager.createQuery("select a from Address a").getResultList();
       // 添加user到数据库，相当于hibernate的save();
        System.out.println(addresses);
//        entityManager.persist(addresses);

        // 5. 提交事务
        transaction.commit();
    }

    //删除数据
    private static void del(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        People people=new People();
        entityManager.remove(people);
        // 添加user到数据库，相当于hibernate的save();
        entityManager.persist(people);

        // 5. 提交事务
        transaction.commit();
    }

    //插入数据
    public static void add(EntityManager entityManager){
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
         Address address=new Address();
        address.setAddress("China");
        People people=new People();
        people.setAddress(address);
        people.setName("jerry");
        people.setSex("男");
        // 添加user到数据库，相当于hibernate的save();
        entityManager.persist(people);

        // 5. 提交事务
        transaction.commit();
    }
}

