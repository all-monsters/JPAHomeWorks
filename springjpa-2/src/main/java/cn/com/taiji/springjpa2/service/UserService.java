package cn.com.taiji.springjpa2.service;
import cn.com.taiji.springjpa2.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Employee findByNameAndPassword(String name,String password);

    Employee findQueryHql(String name,String password);

    Employee findByQuery(String name, String email);

    Page<Employee> findAll(int page,int size,int id);

    Employee saveEmp(Employee employee);

    Employee updateEmp(Employee employee);
    List<Employee> findAll();

}

