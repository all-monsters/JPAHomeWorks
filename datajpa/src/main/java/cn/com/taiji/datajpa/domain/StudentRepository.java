package cn.com.taiji.datajpa.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {

    List<Student> findByFlagAndStuIdIsNullOrderByIdAsc(@Param("flag") int i);

    //删除学生
    @Modifying
    @Query("update Student set flag=0 where id=:id")
    int deleteStu(@Param("id") int i);

    //修改学生
    @Modifying
    @Query("update Student set stuName=:stuName where id=1")
    int updateStu(@Param("stuName") String name);
}
