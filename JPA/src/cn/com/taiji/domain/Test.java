package cn.com.taiji.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

public class Test {
	
	

	public static void main(String[] args) {
		// 1. 创建EntityManagerFactory
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");

		// 2. 创建EntityManager
		EntityManager entityManager = factory.createEntityManager();

//		test(entityManager);

//		findByName(entityManager);

//		addPeople(entityManager);

//		add(entityManager);	 
//		search(entityManager,"tom");

		findPeople(entityManager);

		// 6. 关闭EntityManager
		entityManager.close();

		// 7. 关闭EntityManagerFactory
		factory.close();
	}

	public static void findPeople(EntityManager entityManager) {

		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();


		// 4. 持久化操作
		String name="xiaoming";
		String sex="男";
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<People> c = cb.createQuery(People.class);
		Root<People> peopleRoot = c.from(People.class);
		c.select(peopleRoot);
		c.distinct(true);
//			Join<People,Project> project =
//					peopleRoot.join("projects", JoinType.LEFT);
		List<Predicate> criteria = new ArrayList<Predicate>();
		if (name != null) {
			ParameterExpression<String> p =
					cb.parameter(String.class, "name");
			criteria.add(cb.equal(peopleRoot.get("name"), p));
		}
		if (sex != null) {
            ParameterExpression<String> p =
                    cb.parameter(String.class, "sex");
            criteria.add(cb.equal(peopleRoot.get("sex"), p));
        }
//			if (projectName != null) {
//				ParameterExpression<String> p =
//						cb.parameter(String.class, "project");
//				criteria.add(cb.equal(project.get("name"), p));
//			}
//			if (city != null) {
//				ParameterExpression<String> p =
//						cb.parameter(String.class, "city");
//				criteria.add(cb.equal(emp.get("address").get("city"), p));
//			}
		if (criteria.size() == 0) {
			throw new RuntimeException("no criteria");
		} else if (criteria.size() == 1) {
			c.where(criteria.get(0));
		} else {
			c.where(cb.and(criteria.toArray(new Predicate[0])));
		}

		TypedQuery<People> q = entityManager.createQuery(c);
		if (name != null) { q.setParameter("name", name); }
		if (sex != null) { q.setParameter("sex", sex); }
//			if (project != null) { q.setParameter("project", projectName); }
//			if (city != null) { q.setParameter("city", city); }
		List<People> peopleList= q.getResultList();
		System.out.println(peopleList.size());


		// 添加people到数据库，相当于hibernate的save();
//		entityManager.persist(people);

		// 5. 提交事务
		transaction.commit();

	}




	private static void addPeople(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		Address address=new Address();
		address.setAddress("埃塞俄比亚");

		People people = new People();
		people.setName("daming");
		people.setSex("男");
		people.setAddress(address);

		// 添加people到数据库，相当于hibernate的save();
		entityManager.persist(people);

		// 5. 提交事务
		transaction.commit();
	}

	private static void findByName(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<People> c = cb.createQuery(People.class);
		Root<People> peopleRoot = c.from(People.class);
		c.select(peopleRoot) .where(cb.equal(peopleRoot.get("name"), "xiaoming"));

		TypedQuery query = entityManager.createQuery(c);
		List<People> peopleList= query.getResultList();

		System.out.println(peopleList.size());

		// 添加people到数据库，相当于hibernate的save();
//		entityManager.persist(peopleList);

		// 5. 提交事务
		transaction.commit();
	}

	private static void test(EntityManager entityManager) {
		// 3.开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		// 4. 持久化操作
		List<Authority> authorityList = new ArrayList<>();

		Authority authority = new Authority();
		authority.setName("管理员");
		authorityList.add(authority);

		List<User> userList = new ArrayList<>();

		User user = new User();
		user.setUsername("小明");
		user.setPassword("xiaoming");
		user.setAuthorityList(authorityList);

		// 添加user到数据库，相当于hibernate的save();
		entityManager.persist(user);
		entityManager.persist(authority);

		// 5. 提交事务
		transaction.commit();
	}
}
