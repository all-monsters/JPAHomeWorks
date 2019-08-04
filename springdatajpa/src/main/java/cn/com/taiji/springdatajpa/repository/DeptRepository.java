package cn.com.taiji.springdatajpa.repository;

import cn.com.taiji.springdatajpa.domain.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeptRepository extends JpaRepository<Dept, Integer>, JpaSpecificationExecutor<Dept> {

    List<Dept> findAll();

    //查询有根节点，flag为1，按正序排序
    List<Dept> findByFlagAndParentNotNullOrderByDeptIndexAsc(@Param("flag") int i);

   /* Dept findOne(int id);*/

    /*List<Dept> find();*/
}
