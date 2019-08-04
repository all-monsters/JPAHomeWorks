package cn.com.taiji.datajpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @program: jpa-demo2plus
 * @description:
 * @author: lgppppppp
 * @create: 2019-07-31 10:11
 **/
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    List<User> findByFlagAndBirthdayIsNullOrderByUserIndexAsc(@Param("flag") int i);
}
