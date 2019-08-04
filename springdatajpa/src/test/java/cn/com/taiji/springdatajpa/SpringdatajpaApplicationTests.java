package cn.com.taiji.springdatajpa;

import cn.com.taiji.springdatajpa.domain.Dept;
import cn.com.taiji.springdatajpa.domain.User;
import cn.com.taiji.springdatajpa.service.DeptService;
import cn.com.taiji.springdatajpa.service.UserService;
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
import org.springframework.data.repository.query.Param;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringdatajpaApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private ObjectMapper objectMapper;

    @PersistenceContext
    EntityManager em;


    @Ignore
    @Test
    @Transactional
    //分页查询dept信息
    public void testPageDept() {
        String map = "{\"page\" : 1,\"pageSize\" : 5, \"filter\":{ \"filters\":[{ \"field\" : \"deptName\", \"value\":\"机构8\"}]}}";
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

        Map mapDept = deptService.getPage(searchParameters);


        System.out.println(mapDept.get("total"));

        System.out.println(mapDept.get("depts"));
    }

    //	@Ignore
    @Test
    @Transactional
    //分页查询user信息
    public void testPageUser() {

        String map = "{\"page\" : 1,\"pageSize\" : 5, \"filter\":{ \"filters\":[{ \"field\" : \"userName\", \"value\":\"aaa\"}]}}";
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

        Map mapDept = userService.getPage(searchParameters);


        System.out.println(mapDept.get("total"));

        System.out.println(mapDept.get("users"));
    }

    @Ignore
    @Test
    //查询所有用户信息
    public void findAllUser() {
        userService.selectAll();
        log.info("所有用户信息"+userService.selectAll());
    }

    @Ignore
    @Test
    //添加用户
    public void addUser(){
        User user=new User();
        user.setName("bbb");
        user.setFlag(1);
        userService.add(user);
    }


    @Ignore
    @Test
    //查询有根节点，flag为1，按正序排序
    public void findByFlagAndParentNotNullOrderByDeptIndexAsc(){
        int i=1;
        List<Dept> list = deptService.findByFlagAndParentNotNullOrderByDeptIndexAsc(i);
        //log.info("查询部门"+list);
        System.out.println("查询部门结果数量："+list.size());
    }
}
