package cn.com.taiji.springjpa2.service.Impl;



import cn.com.taiji.springjpa2.dao.UserDao;
import cn.com.taiji.springjpa2.entity.Employee;
import cn.com.taiji.springjpa2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public Employee findByNameAndPassword(String name, String password) {
        return userDao.findByNameAndPassword(name, password);
    }

    @Override
    public Employee findQueryHql(String name, String password) {
        return userDao.findQueryHql(name,password);
    }

    @Override
    public Employee findByQuery(String name, String email) {
        return userDao.findByQuery(name, email);
    }


    @Override
    public Page<Employee> findAll(int page,int size,int id) {
        Pageable pageable =new PageRequest(page,size,new Sort(Sort.Direction.DESC,"id"));
        return userDao.findAll((Specification<Employee>) (root, query, cb) -> {
            Predicate p1 = cb.like(root.get("id").as(String.class),"%"+id+"%");
            return cb.and(p1);
        },pageable);
    }


    @Override
    public Employee saveEmp(Employee employee) {
        return userDao.save(employee);
    }


    @Override
    public Employee updateEmp(Employee employee) {
        return userDao.save(employee);
    }


    @Override
    public List<Employee> findAll() {
        return userDao.findAll();
    }

}
