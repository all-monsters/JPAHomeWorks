package cn.com.taiji.jpafinal.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.NamedQuery;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {

    //查询所有未删除book信息（flag=1）
    @Query("select b from Book b where flag=1")
    List<Book> findByFlag();

    //删除书籍（将flag置为0）
    @Modifying
    @Query("update Book set flag=0 where id=:id")
    int delete(@Param("id") int i);

    //修改书籍（通过id修改书名）
    @Modifying
    @Query("update Book set bookName=:bookName where id=2")
    int update(@Param("bookName") String name);

    //查询书名为。。。的书籍并按照id顺序排序
    List<Book> findBooksByBookNameEqualsOrderByIdAsc(@Param("bookName") String name);



}
