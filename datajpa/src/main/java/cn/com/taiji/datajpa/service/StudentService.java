package cn.com.taiji.datajpa.service;

import cn.com.taiji.datajpa.domain.Student;
import cn.com.taiji.datajpa.domain.StudentRepository;
import cn.com.taiji.datajpa.domain.User;
import cn.com.taiji.datajpa.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: datajpa
 * @description:
 * @author: lgppppppp
 * @create: 2019-08-04 20:31
 **/
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    
    /**  
     * @description: //TODO 分页的动态查询
     * @author: lgppppppp
     * @date: 2019-08-04 20:56 
     * @param: [searchParameters]
     * @return: java.util.Map
     **/
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map getPage(final Map searchParameters) {
        Map map = new HashMap();
        int page = 0;
        int pageSize = 10;
        Page<Student> pageList;
        if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("page") != null) {
            page = Integer.parseInt(searchParameters.get("page").toString()) - 1;
        }
        if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("pageSize") != null) {
            pageSize = Integer.parseInt(searchParameters.get("pageSize").toString());
        }
        if (pageSize < 1)
            pageSize = 1;
        if (pageSize > 100)
            pageSize = 100;
        //获取排序方式
        List<Map> orderMaps = (List<Map>) searchParameters.get("sort");
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (orderMaps != null) {
            for (Map m : orderMaps) {
                if (m.get("field") == null)
                    continue;
                String field = m.get("field").toString();
                if (!StringUtils.isEmpty(field)) {
                    String dir = m.get("dir").toString();
                    if ("DESC".equalsIgnoreCase(dir)) {
                        orders.add(new Sort.Order(Sort.Direction.DESC, field));
                    } else {
                        orders.add(new Sort.Order(Sort.Direction.ASC, field));
                    }
                }
            }
        }
        PageRequest pageable;
        if (orders.size() > 0) {
            pageable = PageRequest.of(page, pageSize, Sort.by(orders));
        } else {
            Sort sort = new Sort(Sort.Direction.ASC, "id");
            pageable = PageRequest.of(page, pageSize, sort);
        }
        Map filter = (Map) searchParameters.get("filter");
        if (filter != null) {
            final List<Map> filters = (List<Map>) filter.get("filters");
            Specification<Student> spec = new Specification<Student>() {
                @Override
                public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> pl = new ArrayList<Predicate>();
                    for (Map f : filters) {
                        String field = f.get("field").toString().trim();
                        String value = f.get("value").toString().trim();
                        if (value != null && value.length() > 0) {
                            if ("stuName".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), value + "%"));
                            }
                        }

                    }
                    // 查询出未删除的
                    pl.add(cb.equal(root.<Integer>get("flag"), 1));
                    return cb.and(pl.toArray(new Predicate[0]));
                }
            };


            pageList = studentRepository.findAll(spec, pageable);

        } else {
            Specification<Student> spec = new Specification<Student>() {
                public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    // 查询出未删除的
                    list.add(cb.equal(root.<Integer>get("flag"), 1));
                    return cb.and(list.toArray(new Predicate[0]));
                }
            };
            pageList = studentRepository.findAll(spec, pageable);

        }
        map.put("total", pageList.getTotalElements());
        List<Student> list = pageList.getContent();

        map.put("students", list);
        return map;
    }

    /**
     * @description: //TODO 查询全部学生
     * @author: lgppppppp
     * @date: 2019-08-04 21:03
     * @param:
     * @return:
     **/
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    /**
     * @description: //TODO 添加学生
     * @author: lgppppppp
     * @date: 2019-08-04 21:15
     * @param: [user]
     * @return: void
     **/
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveStudent(Student student) {
        this.studentRepository.saveAndFlush(student);
        System.out.println(this.studentRepository.findAll().size());
    }

    /**
     * @description: //TODO 查询学号不为空按照id升序
     * @author: lgppppppp
     * @date: 2019-08-04 21:17
     * @param: []
     * @return: java.util.List<cn.com.taiji.datajpa.domain.Student>
     **/
    public List<Student> selectStudent(){
        return this.studentRepository.findByFlagAndStuIdIsNullOrderByIdAsc(1);
    }

    /**
     * @description: //TODO 删除学生
     * @author: lgppppppp
     * @date: 2019-08-04 21:23
     * @param: [i]
     * @return: void
     **/
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteStu(int i){
        studentRepository.deleteStu(i);
    }

    /**  
     * @description: //TODO 修改学生
     * @author: lgppppppp
     * @date: 2019-08-04 21:28
     * @param: [name]
     * @return: void
     **/
    //更新书籍
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateStu(String name){
        studentRepository.updateStu(name);
    }
}
