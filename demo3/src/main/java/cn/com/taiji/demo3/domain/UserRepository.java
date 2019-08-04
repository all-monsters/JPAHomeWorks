package cn.com.taiji.demo3.domain;

import cn.com.taiji.demo3.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

}
