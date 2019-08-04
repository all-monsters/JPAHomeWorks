package cn.com.taiji.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import javax.persistence.*;
import javax.persistence.criteria.*;

public class Test {
    public static void main(String[] args) {
        // 1. 创建EntityManagerFactory(实体管理器工厂)
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa");

        // 2. 创建EntityManager（实体管理器）
        EntityManager entityManager = factory.createEntityManager();

        //添加address，查询全部people
//        test(entityManager);

        //根据id查询address（原生sql）
//        selectAddress(entityManager);

       // ManyToMany
//        test2(entityManager);

        //JPQL查询方式转换
//        test3(entityManager);

        //动态查询
//        test4(entityManager);

        //修改people
//        updatePeople(entityManager);

        //删除people
//        deletePeople(entityManager);

        //find 根据id查询（立即加载）
//        findPeople(entityManager);

        //getReference 根据id查询(懒加载)
//        getReferencePeople(entityManager);

        //jpql分页查询
//        testPage(entityManager);

        //jpql条件查询
//        testCondition(entityManager);

        // 6. 关闭EntityManager
        entityManager.close();

        // 7. 关闭EntityManagerFactory
        factory.close();
    }

    /**
     * @description: //TODO jpql条件查询
     * @author: lgppppppp
     * @date: 2019-07-31 09:33
     * @param: [entityManager]
     * @return: void
     **/
    private static void testCondition(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        String jpql="select sex from People where name like ?1";
        Query query=entityManager.createQuery(jpql);
        query.setParameter(1,"乔占广");
        List list=query.getResultList();
        for (Object obj : list){
            System.out.println(obj);
        }
        // 5. 提交事务
        transaction.commit();
    }

    /**
     * @description: //TODO jpql分页查询
     * @author: lgppppppp
     * @date: 2019-07-31 09:07
     * @param: [entityManager]
     * @return: void
     **/
    private static void testPage(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        String jpql="select a from People a";
        Query query=entityManager.createQuery(jpql);
        //开始索引
        query.setFirstResult(0);
        //每页查询的条数
        query.setMaxResults(2);
        List list=query.getResultList();
        for (Object obj : list){
            System.out.println(obj);
        }
        // 5. 提交事务
        transaction.commit();
    }

    /**
     * @description: //TODO getReference 根据id查询(懒加载)
     * @author: lgppppppp
     * @date: 2019-07-31 08:55
     * @param: [entityManager]
     * @return: void
     **/
    private static void getReferencePeople(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        People people=entityManager.getReference(People.class,1);
        //只有在用到的时候（比如：输出）才会执行sql语句进行查询
        System.out.println(people);
        // 5. 提交事务
        transaction.commit();
    }

    /**
     * @description: //TODO find查询（立即加载）
     * @author: lgppppppp
     * @date: 2019-07-31 08:37
     * @param: [entityManager]
     * @return: void
     **/
    private static void findPeople(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        People people=entityManager.find(People.class,1);
        System.out.println(people);
        // 5. 提交事务
        transaction.commit();
    }

    /**
     * @description: //TODO 删除people
     * @author: lgppppppp
     * @date: 2019-07-30 18:59
     * @param: [entityManager]
     * @return: void
     **/
    private static void deletePeople(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        People people = entityManager.find(People.class,11);
        entityManager.remove(people);
        // 5. 提交事务
        transaction.commit();
    }

    /**
     * @description: //TODO 修改people
     * @author: lgppppppp
     * @date: 2019-07-30 18:34
     * @param: [entityManager]
     * @return: void
     **/
    private static void updatePeople(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        People people = entityManager.find(People.class,1);
        people.setName("乔占广");
        people.setSex("男");
        entityManager.merge(people);
        // 5. 提交事务
        transaction.commit();
    }

    /**
     * @description: //TODO 动态查询
     * @author: lgppppppp
     * @date: 2019-07-30 18:33
     * @param: [entityManager]
     * @return: java.util.List<cn.com.taiji.domain.People>
     **/
    public static List<People> test4(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        String name="qzg";
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<People> c = cb.createQuery(People.class);
        Root<People> emp = c.from(People.class);
        c.select(emp);
        c.distinct(true);
        List<Predicate> criteria = new ArrayList<Predicate>();
        if (name != null) {
            ParameterExpression<String> p =
                    cb.parameter(String.class, "name");
            criteria.add(cb.equal(emp.get("name"), p));
        }
        if (criteria.size() == 0) {
            throw new RuntimeException("no criteria");
        } else if (criteria.size() == 1) {
            c.where(criteria.get(0));
        } else {
            c.where(cb.and(criteria.toArray(new Predicate[0])));
        }
        TypedQuery<People> q = entityManager.createQuery(c);
        if (name != null) { q.setParameter("name", name); }
        //5.提交事务
        transaction.commit();
        return q.getResultList();
    }

    /**
     * @description: //TODO JPQL查询方式转换
     * @author: lgppppppp
     * @date: 2019-07-30 18:33
     * @param: [entityManager]
     * @return: void
     **/
    private static void test3(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<People> c = cb.createQuery(People.class);
        Root<People> emp = c.from(People.class);
        c.select(emp).where(cb.equal(emp.get("name"), "乔占广"));
        TypedQuery query=entityManager.createQuery(c);
        List<People> peopleList=query.getResultList();

        System.out.println(peopleList.size());
        // 5. 提交事务
        transaction.commit();
    }

    /**
     * @description: //TODO ManytoMany
     * @author: lgppppppp
     * @date: 2019-07-30 18:33
     * @param: [entityManager]
     * @return: void
     **/
    private static void test2(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        List<Authority> authorityList=new ArrayList<>();

        Authority authority=new Authority();
        User user=new User();

        authority.setName("乔占广");

        user.setUsername("qzg");
        user.setPassword("1234");

        authorityList.add(authority);
        user.setAuthorityList(authorityList);

        // 添加user到数据库，相当于hibernate的save();
        entityManager.persist(user);
        entityManager.persist(authority);



        // 5. 提交事务
        transaction.commit();
    }

    /**
     * @description: //TODO 根据id查询address（原生sql）
     * @author: lgppppppp
     * @date: 2019-07-30 18:33
     * @param: [entityManager]
     * @return: void
     **/
    private static void selectAddress(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
       Address address= (Address) entityManager.createNativeQuery("select * from Address where id=?1",Address.class).setParameter(1,1)
               .getSingleResult();
        System.out.println(address.toString());
        // 5. 提交事务
        transaction.commit();
    }

    /**
     * @description: //TODO 添加address，查询全部people
     * @author: lgppppppp
     * @date: 2019-07-30 18:34
     * @param: [entityManager]
     * @return: void
     **/
    private static void test(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
//        List<People> list = entityManager.createQuery("select a from People a")
//                .getResultList();
//        System.out.println(list.size());
        Address address = new Address();
        address.setAddress("内蒙");
        People a = new People();
        a.setName("乔占广");
        a.setSex("女");
        a.setAddress(address);
        // 添加user到数据库，相当于hibernate的save();
        entityManager.persist(a);

        // 5. 提交事务
        transaction.commit();
    }
}
