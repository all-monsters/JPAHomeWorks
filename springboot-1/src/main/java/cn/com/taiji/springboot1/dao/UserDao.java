package cn.com.taiji.springboot1.dao;
import java.util.List;

public abstract class UserDao {
    /**
     * 向数据库中保存一个新用户
     *
     * @param user 要保存的用户对象
     * @return 是否增肌成功
     */
    public abstract Boolean add(UserDO user);

    /**
     * 更新数据库中的一个用户
     *
     * @param user 要更新的用户对象
     * @return 是否更新成功
     */
    public abstract Boolean update(UserDO user);

    /**
     * 删除一个指定的用户
     *
     * @param id 要删除的用户的标识
     * @return 是否删除成功
     */
    public abstract boolean delete(Long id);

    /**
     * 精确查询一个指定的用户
     *
     * @param id 要查询的用户的标识
     * @return 如果能够查询到，返回用户信息，否则返回 null
     */
    public abstract UserDO locate(Long id);

    /**
     * 通过名称模糊查询用户
     *
     * @param name 要模糊查询的名称
     * @return 查询到的用户列表
     */
    public abstract List<UserDO> matchName(String name);
}
