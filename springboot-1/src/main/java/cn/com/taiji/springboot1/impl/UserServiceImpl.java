package cn.com.taiji.springboot1.impl;

import cn.com.taiji.springboot1.dao.UserDO;
import cn.com.taiji.springboot1.dao.UserDao;
import cn.com.taiji.springboot1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class UserServiceImpl extends UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    public UserDO add(UserDO user) {
        user.setId(new Date().getTime());
        if (userDao.add(user)) {
            return user;
        }
        return null;
    }


    public UserDO update(UserDO user) {
        if (userDao.update(user)) {
            return locate(user.getId());
        }
        return null;
    }


    public boolean delete(Long id) {
        return userDao.delete(id);
    }


    public UserDO locate(Long id) {
        return userDao.locate(id);
    }


    public List<UserDO> matchName(String name) {
        return userDao.matchName(name);
    }
}


