package cn.com.taiji.springboot1.web;

import cn.com.taiji.springboot1.bo.RestCollectionResult;
import cn.com.taiji.springboot1.bo.RestItemResult;
import cn.com.taiji.springboot1.dao.UserDO;
import cn.com.taiji.springboot1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class UserApi {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RestItemResult<UserDO> add(@RequestBody UserDO user) {
        RestItemResult<UserDO> result = new RestItemResult<>();
        user = userService.add(user);
        if (user != null) {
            result.setItem(user);
            result.setResult("success");
        } else {
            result.setMessage("新增用户失败");
            result.setResult("failure");
        }
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestItemResult<UserDO> update(@RequestBody UserDO user) {
        RestItemResult<UserDO> result = new RestItemResult<>();
        user = userService.update(user);
        if (user != null) {
            result.setItem(user);
            result.setResult("success");
        } else {
            result.setMessage("修改用户失败");
            result.setResult("failure");
        }
        return result;
    }

    @RequestMapping(value = "/delete/{uuid}", method = RequestMethod.GET)
    public RestItemResult<UserDO> delete(@PathVariable Long uuid) {
        RestItemResult<UserDO> result = new RestItemResult<>();
        if (userService.delete(uuid)) {
            result.setResult("success");
        } else {
            result.setMessage("删除用户失败");
            result.setResult("failure");
        }
        return result;
    }

    @RequestMapping(value = "/locate/{uuid}", method = RequestMethod.GET)
    public RestItemResult<UserDO> locate(@PathVariable Long uuid) {
        RestItemResult<UserDO> result = new RestItemResult<>();
        UserDO user = userService.locate(uuid);
        if (user != null) {
            result.setItem(user);
            result.setResult("success");
        } else {
            result.setMessage("查询用户失败");
            result.setResult("failure");
        }
        return result;
    }

    @RequestMapping(value = "/match/{name}", method = RequestMethod.GET)
    public RestCollectionResult<UserDO> match(@PathVariable String name) {
        RestCollectionResult<UserDO> result = new RestCollectionResult<>();
        List<UserDO> users = userService.matchName(name);
        result.setItems(users);
        result.setResult("success");
        return result;
    }
}
