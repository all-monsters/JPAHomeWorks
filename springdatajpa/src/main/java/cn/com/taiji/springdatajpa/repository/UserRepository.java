package cn.com.taiji.springdatajpa.repository;

import cn.com.taiji.springdatajpa.domain.Dept;
import cn.com.taiji.springdatajpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @program: springdatajpa
 * @description: UserRepository
 * @author: qiao zhan guang
 * @create: 2019-07-31 10:10
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    //查询所有用户信息（flag=1，即未删除用户）
    @Query("select u from User u where flag=1")
    List<User> findAll();

    @Query("select u from User u where flag=1")
    //查询无根节点，flag为1，按正序排序
    List<User> findByFlagAndParentIsNullOrderByUSerIndexAsc(@Param("flag") int i);
}
