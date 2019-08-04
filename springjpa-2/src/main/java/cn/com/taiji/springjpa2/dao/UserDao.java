package cn.com.taiji.springjpa2.dao;

import cn.com.taiji.springjpa2.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<Employee,String> {

    Employee findByNameAndPassword(String name,String password);


    @Query(value = "select e from Employee e where name =?1 and password=?2")
    Employee findQueryHql(String name,String password);


    @Query(value = "select * from employee  where name =?1 and email=?2",nativeQuery =true)
    Employee findByQuery(String name, String email);


    Page<Employee> findAll(Specification<Employee> spec,Pageable pageable);
}
