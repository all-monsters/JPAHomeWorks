package cn.com.taiji.domain;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @program: jpa-demo2
 * @description:
 * @author: lgppppppp
 * @create: 2019-07-30 19:02
 **/
public class TestAuthor {
    public static void main(String[] args) {
        // 1. 创建EntityManagerFactory
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa");

        // 2. 创建EntityManager
        EntityManager entityManager = factory.createEntityManager();

        // 添加author和article
//        addAuthor(entityManager);

		//查询全部author
//		selectAuthor(entityManager);

        //条件查询
//		selectByName(entityManager);

		//删除author
//		delAuthor(entityManager);

		//修改author
		updateAuthor(entityManager);


        // 6. 关闭EntityManager
        entityManager.close();

        // 7. 关闭EntityManagerFactory
        factory.close();
    }

    /**
     * @description: //TODO 修改author
     * @author: lgppppppp
     * @date: 2019-07-31 17:16
     * @param: [entityManager]
     * @return: void
     **/
    private static void updateAuthor(EntityManager entityManager) {
        //开启事务
        EntityTransaction entityTransaction=entityManager.getTransaction();
        entityTransaction.begin();
        //进行持久化
        Author author= (Author) entityManager.find(Author.class,2);
        author.setName("lgp");
        entityManager.merge(author);
        //提交事务
        entityTransaction.commit();
    }

    /**
     * @description: //TODO 删除author
     * @author: lgppppppp
     * @date: 2019-07-31 17:16
     * @param: [entityManager]
     * @return: void
     **/
    private static void delAuthor(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        Author author=entityManager.find(Author.class,1);
        entityManager.remove(author);

        // 5. 提交事务
        transaction.commit();
    }

    /**
     * @description: //TODO 条件查询
     * @author: lgppppppp
     * @date: 2019-07-31 17:14
     * @param: [entityManager]
     * @return: void
     **/
    private static void selectByName(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> c=cb.createQuery(Author.class);
        Root<Author> root=c.from(Author.class);
        c.select(root).where(cb.equal(root.get("name"),"李国平"));
        TypedQuery query=entityManager.createQuery(c);
        List<Author> list=query.getResultList();
        System.out.println(list);
        // 5. 提交事务
        transaction.commit();
    }

    /**
     * @description: //TODO 查询全部author
     * @author: lgppppppp
     * @date: 2019-07-31 17:00
     * @param: [entityManager]
     * @return: void
     **/
    private static void selectAuthor(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> c=cb.createQuery(Author.class);
        Root<Author> emp=c.from(Author.class);
        c.select(emp);
        c.distinct(true);
        TypedQuery query=entityManager.createQuery(c);
        List<Author> list=query.getResultList();
        System.out.println(list);
        // 5. 提交事务
        transaction.commit();
    }

    /**
     * @description: //TODO 添加author和article
     * @author: lgppppppp
     * @date: 2019-07-31 16:54
     * @param: [entityManager]
     * @return: void
     **/
    private static void addAuthor(EntityManager entityManager) {
        // 3.开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 4. 持久化操作
        Author author=new Author();
        author.setName("李国平");
        Article article=new Article();
        article.setTitle("乔占广 ");
        article.setContent("lgp and qzg");
        article.setAuthor(author);
        entityManager.persist(author);
        entityManager.persist(article);
        // 5. 提交事务
        transaction.commit();
    }
}