package cn.com.taiji.springjpa2.controller;
//package com.example.demo.controller;

import cn.com.taiji.springjpa2.entity.Employee;
import cn.com.taiji.springjpa2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/emp")
    public Employee save(Employee emp){
        return userService.saveEmp(emp);
    }
    @PutMapping("/emp")
    public Employee update(Employee emp){
        return userService.updateEmp(emp);
    }

    @GetMapping("/emp/findByNameAndPassword")
    public Employee findByNameAndPassword(String name,String password){
        return userService.findByNameAndPassword(name,password);
    }
    @GetMapping("/emp/findQueryHql")
    public Employee findQueryHql(String name,String password){
        return userService.findQueryHql(name,password);
    }
    @GetMapping("/emp/findByQuery")
    public Employee findByQuery(String name, String email){
        return userService.findByQuery(name,email);
    }

    /**
     *
     * @param page 由于page是从0开始的，但是前端是从1 开始的  所以要减一
     * @param size
     * @param id
     * @return
     */
    @GetMapping("/emp/findAll")
    public Page<Employee> findAll(int page,int size,int id){
        page =page -1;
        return userService.findAll(page,size, id);
    }
    /**
     * 调用jpa封装好的 查询所有方法
     * @return
     */
    @GetMapping("/emp/all")
    public List<Employee>  findJpaAll(){
        return userService.findAll();
    }

}
