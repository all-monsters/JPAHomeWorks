package cn.com.taiji.demo3;

import cn.com.taiji.demo3.domain.Dept;
import cn.com.taiji.demo3.domain.User;
import cn.com.taiji.demo3.service.DeptSerivce;
import cn.com.taiji.demo3.service.UserService;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jnlp.PersistenceService;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class Demo3ApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(Demo3ApplicationTests.class);

    @Autowired
    DeptSerivce deptService;

    @Autowired
    UserService userService;

    @Autowired
    private ObjectMapper objectMapper;
    private PersistenceService mapUser;

    //	@Ignore
    @Test
    public void testPage() {
        String map = "{\"page\" : 1,\"pageSize\" : 5, \"filter\":{ \"filters\":[{ \"field\" : \"userName\", \"value\":\"用户8\"}]}}";
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

        Map mapUser = userService.getPage(searchParameters);


        System.out.println(mapUser.get("total"));

        System.out.println(mapUser.get("users"));
    }


}
