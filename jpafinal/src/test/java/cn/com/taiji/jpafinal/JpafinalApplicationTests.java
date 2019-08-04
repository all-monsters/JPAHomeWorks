package cn.com.taiji.jpafinal;

import cn.com.taiji.jpafinal.domain.Book;
import cn.com.taiji.jpafinal.service.BookService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpafinalApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    //@Ignore
    @Test
    @Transactional
    //分页查询book信息
    public void testPageBook() {
        //查询书名为绿野仙踪，第一排序方式为bookNumber逆序，第二排序方式为remark逆序的书籍
        String map = "{\"page\" : 1," +
                "\"pageSize\" : 5, " +
                "\"filter\":{ \"filters\":[{ \"field\" : \"bookName\", \"value\":\"绿野仙踪\"}]}," +
                "\"sort\":[{ \"field\" : \"bookNumber\", \"dir\":\"desc\"},{ \"field\" : \"remark\", \"dir\":\"desc\"}]}";
        Map searchParameters =new HashMap();
        try {
            searchParameters = objectMapper.readValue(map, new TypeReference<Map>() {
            });
        } catch (JsonParseException e) {
            log.error("JsonParseException{}:", e.getMessage());
        } catch (JsonMappingException e) {
            log.error("JsonMappingException{}:", e.getMessage());
        } catch (IOException e) {
            log.error("IOException{}:", e.getMessage());
        }

        Map mapBook = bookService.getPage(searchParameters);


        System.out.println(mapBook.get("total"));

        System.out.println(mapBook.get("books"));
    }

    @Ignore
    @Test
    //查询所有book信息
    public void findAllBook() {
        log.info("所有书籍信息"+bookService.findAll());
    }

    @Ignore
    @Test
    //查询所有未删除book信息
    public void findAllBookFlag() {
        log.info("所有未删除书籍信息"+bookService.findAllBookFlag());
    }

    @Ignore
    @Test
    //添加书籍
    public void addBook(){
        Book book=new Book();
        book.setBookName("哈利波特");
        book.setBookNumber("123");
        book.setFlag(1);
        log.info("添加的book"+bookService.add(book));
    }

    @Ignore
    @Test
    //删除书籍（一般不用此方法删除）
    public void remove(){
        bookService.remove(3);
    }

    @Ignore
    @Test
    //删除书籍（常用此方法删除）
    public void delete(){
        bookService.delete(2);
    }

    @Ignore
    @Test
    //更新书籍
    public void update(){
        bookService.update("绿野仙踪");
    }

    @Ignore
    @Test
    //查询书名为。。。的书籍并按照id顺序排序
    public void findBooksByBookNameEqualsOrderByIdAsc(){
        log.info("bookname"+bookService.findBooksByBookNameEqualsOrderByIdAsc("绿野仙踪"));;
    }

}
