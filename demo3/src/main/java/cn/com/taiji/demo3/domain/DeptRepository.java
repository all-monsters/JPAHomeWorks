package cn.com.taiji.demo3.domain;

import cn.com.taiji.demo3.domain.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeptRepository extends JpaRepository<Dept, Integer>, JpaSpecificationExecutor<Dept> {
    List<Dept> findByFlagAndParentIsNullOrderByDeptIndexAsc(@Param("flag") int i);
   

}
