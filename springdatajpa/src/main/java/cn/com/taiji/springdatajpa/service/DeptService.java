package cn.com.taiji.springdatajpa.service;

import cn.com.taiji.springdatajpa.domain.Dept;
import cn.com.taiji.springdatajpa.repository.DeptRepository;

import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: springdatajpa
 * @description: DeptService
 * @author: qiao zhan guang
 * @create: 2019-07-31 10:18
 */
@Service
public class DeptService {
    @Autowired
    private DeptRepository deptRepository;


    /**
     *
     * @Description 功能描述,dept分页查询
     * @Author wanghw
     * @CreatDate 2018年1月31日
     * @param searchParameters
     * @return 返回值Map map类型 total:总条数 depts:查询结果list集合
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map getPage(final Map searchParameters) {
        Map map = new HashMap();
        int page = 0;
        int pageSize = 10;
        Page<Dept> pageList;
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
        List<Map> orderMaps = (List<Map>) searchParameters.get("sort");
        List<Order> orders = new ArrayList<Order>();
        if (orderMaps != null) {
            for (Map d : orderMaps) {
                if (d.get("field") == null)
                    continue;
                String field = d.get("field").toString();
                if (!StringUtils.isEmpty(field)) {
                    String dir = d.get("dir").toString();
                    if ("DESC".equalsIgnoreCase(dir)) {
                        orders.add(new Order(Direction.DESC, field));
                    } else {
                        orders.add(new Order(Direction.ASC, field));
                    }
                }
            }
        }
        PageRequest pageable;
        if (orders.size() > 0) {
            pageable =  PageRequest.of(page, pageSize, Sort.by(orders));
        } else {
            Sort sort = new Sort(Direction.ASC, "deptIndex");
            pageable = PageRequest.of(page, pageSize, sort);
        }
        Map filter = (Map) searchParameters.get("filter");
        if (filter != null) {
            final List<Map> filters = (List<Map>) filter.get("filters");
            Specification<Dept> spec = new Specification<Dept>() {
                @Override
                public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> pl = new ArrayList<Predicate>();
                    for (Map f : filters) {
                        String field = f.get("field").toString().trim();
                        String value = f.get("value").toString().trim();
                        if (value != null && value.length() > 0) {
                            if ("deptName".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.get(field), value + "%"));
                            } else if ("deptIndex".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), value + "%"));
                            } else if ("deptDesc".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), value + "%"));
                            }
                        }

                    }
                    // 查询出未删除的
                    pl.add(cb.equal(root.<Integer>get("flag"), 1));
                    return cb.and(pl.toArray(new Predicate[0]));
                }
            };


            pageList = deptRepository.findAll(spec, pageable);

        } else {
            Specification<Dept> spec = new Specification<Dept>() {
                public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    // 查询出未删除的
                    list.add(cb.equal(root.<Integer>get("flag"), 1));
                    return cb.and(list.toArray(new Predicate[0]));
                }
            };
            pageList = deptRepository.findAll(spec, pageable);

        }
        map.put("total", pageList.getTotalElements());
        List<Dept> list = pageList.getContent();

        map.put("depts", list);
        return map;
    }

    /**
     *
     * @Description 按用户Id查询机构信息
     * @Author chixue
     * @CreatDate 2017年5月2日
     * @UpdateUser wanghw
     * @UpdateDate 2018年1月12日
     * @return 返回值DeptDto
     */
    public Dept findDeptById(int id) {
//		Dept dept = this.deptRepository.findById(id);
//		this.deptRepository.findByFlagAndParentIsNullOrderByDeptIndexAsc(1);
        Optional<Dept> de= this.deptRepository.findById(id);
        Dept dept=de.get();
        return dept;
    }

    //查询有根节点，flag为1，按正序排序
    @Transactional(propagation = Propagation.REQUIRED)
    public List<Dept> findByFlagAndParentNotNullOrderByDeptIndexAsc(int  flag){
      return deptRepository.findByFlagAndParentNotNullOrderByDeptIndexAsc(flag);
    }
}
