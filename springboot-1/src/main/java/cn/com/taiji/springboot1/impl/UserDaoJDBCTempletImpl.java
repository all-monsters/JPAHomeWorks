package cn.com.taiji.springboot1.impl;


import cn.com.taiji.springboot1.dao.UserDO;

import cn.com.taiji.springboot1.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoJDBCTempletImpl extends UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoJDBCTempletImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Boolean add(UserDO user) {
        String sql = "INSERT INTO AUTH_USER(UUID,NAME) VALUES(?,?)";
        return jdbcTemplate.update(sql, user.getId(), user.getName()) > 0;
    }


    public Boolean update(UserDO user) {
        String sql = "UPDATE AUTH_USER SET NAME = ? WHERE UUID = ?";
        return jdbcTemplate.update(sql, user.getName(), user.getId()) > 0;
    }


    public boolean delete(Long id) {
        String sql = "DELETE FROM AUTH_USER WHERE UUID = ?";
        return jdbcTemplate.update(sql, id) > 0;

    }


    public UserDO locate(Long id) {
        String sql = "SELECT * FROM AUTH_USER WHERE UUID=?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, id);

        if (rs.next()) {
            return generateEntity(rs);
        }
        return null;
    }


    public List<UserDO> matchName(String name) {
        String sql = "SELECT * FROM AUTH_USER WHERE NAME LIKE ?";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, "%" + name + "%");
        List<UserDO> users = new ArrayList<>();
        while (rs.next()) {
            users.add(generateEntity(rs));
        }
        return users;
    }

    private UserDO generateEntity(SqlRowSet rs) {
        UserDO weChatPay = new UserDO();
        weChatPay.setId(rs.getLong("UUID"));
        weChatPay.setName(rs.getString("NAME"));
        return weChatPay;
    }
}