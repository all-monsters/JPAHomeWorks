package cn.com.taiji.datajpa;

import cn.com.taiji.datajpa.domain.Dept;
import cn.com.taiji.datajpa.domain.Student;
import cn.com.taiji.datajpa.domain.User;
import cn.com.taiji.datajpa.service.DeptService;
import cn.com.taiji.datajpa.service.StudentService;
import cn.com.taiji.datajpa.service.UserService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatajpaApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(DatajpaApplicationTests.class);

    @Autowired
    private UserService userService;

    @Autowired
    DeptService deptService;

    @Autowired
    StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @PersistenceContext
    EntityManager em;

    /**
     * @description: //TODO dept动态分页查询
     * @author: lgppppppp
     * @date: 2019-07-31 15:57
     * @param: []
     * @return: void
     **/
    @Ignore
    @Test
    @Transactional
    public void testPage() {
        String map = "{\"page\" : 1,\"pageSize\" : 5, \"filter\":{ \"filters\":[{ \"field\" : \"deptName\", \"value\":\"机构8\"}]}}";
        Map searchParameters = new HashMap();
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

    /**
     * @description: //TODO user动态分页查询
     * @author: lgppppppp
     * @date: 2019-07-31 15:57
     * @param: []
     * @return: void
     **/
    @Ignore
    @Test
    @Transactional
    public void testUserPage() {
        String map = "{\"page\" : 1,\"pageSize\" : 5, \"filter\":{ \"filters\":[{ \"field\" : \"userName\", \"value\":\"qzg\"}]}}";
        Map searchParameters = new HashMap();
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


    //保存User
    @Ignore
    @Test
    public void saveUser() {
        User user = new User();
        user.setName("123");
        user.setUserName("qzg");
        user.setFlag(1);
        userService.saveUser(user);
        System.out.println(user);
    }

    //查询User
    @Ignore
    @Test
    public void selectUser() {
        List<User> list = userService.selectUser();
        System.out.println(list);
    }

    /**  
     * @description: //TODO 动态分页查询学生信息
     * @author: lgppppppp
     * @date: 2019-08-04 21:27 
     * @param: []
     * @return: void
     **/
    @Ignore
    @Test
    @Transactional
    public void selectStu() {
        String map = "{\"page\" : 1,\"pageSize\" : 5, \"filter\":{ \"filters\":[{ \"field\" : \"stuName\", \"value\":\"乔占广\"}]}}";
        Map searchParameters = new HashMap();
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

        System.out.println(mapDept.get("students"));
    }

    /**
     * @description: //TODO 查询全部学生信息
     * @author: lgppppppp
     * @date: 2019-08-04 21:29
     * @param: []
     * @return: void
     **/
    @Ignore
    @Test
    public void findAllStudent() {
        log.info("所有学生信息"+studentService.findAll());
    }

    /**  
     * @description: //TODO 添加学生信息
     * @author: lgppppppp
     * @date: 2019-08-04 21:59 
     * @param: []
     * @return: void
     **/
//    @Ignore
    @Test
    public void saveStudent(){
        Student student=new Student();
        student.setStuId(111);
        student.setStuName("乔占广");
        student.setFlag(1);
        studentService.saveStudent(student);
        System.out.println(student);
    }

    /**
     * @description: //TODO 删除学生信息
     * @author: lgppppppp
     * @date: 2019-08-04 21:33
     * @param: []
     * @return: void
     **/
    @Ignore
    @Test
    public void deleteStudent(){
        studentService.deleteStu(1);
    }

    /**
     * @description: //TODO 修改学生信息
     * @author: lgppppppp
     * @date: 2019-08-04 21:34
     * @param: []
     * @return: void
     **/
    @Ignore
    @Test
    public void updateStudent(){
        studentService.updateStu("李国平");
    }
}
