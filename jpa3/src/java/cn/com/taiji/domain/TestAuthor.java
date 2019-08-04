package cn.com.taiji.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

//one to many 测试类
public class TestAuthor {

	@PersistenceContext
	EntityManager em;

	public static void main(String[] args) {
		// 1. 创建EntityManagerFactory
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");

		// 2. 创建EntityManager
		EntityManager entityManager = factory.createEntityManager();

		   // 添加作者
		  addAuthor(entityManager);

		  /*//添加文章
		 addArticle(entityManager);*/

		/*  //查询所有作者
		search(entityManager);*/

		/* //删除作者信息
		delAuthor(entityManager);*/

		/*//修改作者信息
		updateAuthor(entityManager);*/

         /* //根据名字查询作者
		searchByNameQuery(entityManager);*/

		// 6. 关闭EntityManager
		entityManager.close();

		// 7. 关闭EntityManagerFactory
		factory.close();
	}


	//根据名字查询作者
	private static void searchByNameQuery(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		CriteriaBuilder cb=entityManager.getCriteriaBuilder();
		CriteriaQuery<Author> c=cb.createQuery(Author.class);
		Root<Author> root=c.from(Author.class);
		c.select(root).where(cb.equal(root.get("name"),"jackson"));
		TypedQuery query=entityManager.createQuery(c);
		List<Author> list=query.getResultList();
		System.out.println(list);
		// 5. 提交事务
		transaction.commit();
	}

	//修改作者信息
	private static void updateAuthor(EntityManager entityManager) {
		//开启事务
		EntityTransaction entityTransaction=entityManager.getTransaction();
		entityTransaction.begin();
		//进行持久化
		Author author= (Author) entityManager.find(Author.class,6);
		author.setName("bbb");
		entityManager.merge(author);
		//提交事务
		entityTransaction.commit();
	}

	//删除作者信息
	private static void delAuthor(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		Author author=entityManager.find(Author.class,7);
		entityManager.remove(author);

		// 5. 提交事务
		transaction.commit();
	}

	//查询所有作者
	private static void search(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		CriteriaBuilder cb=entityManager.getCriteriaBuilder();
		CriteriaQuery<Author> c=cb.createQuery(Author.class);
		Root<Author> root=c.from(Author.class);
		c.select(root);
		TypedQuery query=entityManager.createQuery(c);
		List<Author> list=query.getResultList();
		System.out.println(list);
		// 5. 提交事务
		transaction.commit();
	}

	//添加文章
	private static void addArticle(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		Article article=new Article();
		article.setTitle("data save");
		article.setContent("how to save");
		entityManager.persist(article);
		// 5. 提交事务
		transaction.commit();
	}

	// 添加作者
	private static void addAuthor(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		Author author=new Author();
		author.setName("Jerry");
		Article article=new Article();
		article.setTitle("data ");
		article.setContent("how to ");
		article.setAuthor(author);

		entityManager.persist(author);
		entityManager.persist(article);


		// 5. 提交事务
		transaction.commit();
	}


}
