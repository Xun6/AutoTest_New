package com.TestInterfaceDevelopMapper.service;

import com.TestInterfaceDevelopMapper.model.User;
import com.github.pagehelper.PageInfo;

public interface UserService {
    /**
     * 用户登陆
     * @param user
     * @return
     */
    Integer login(User user);

    /**
     * 全量查询用户
     * @param user
     * @param page 页数
     * @param size 每页数据量
     * @return
     */
    PageInfo<User> selectAll(User user, int page, int size);

    /**
     * 更新用户
     * @param user
     * @return
     */
    Integer updateUser(User user);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    Integer deleteUser(int userId);

    /**
     * 添加用户
     * @param insert
     * @return
     */
    Integer insertUser(User insert);

    /**
     * 删除数据表主键字段
     */
    void deletePrimaryKeyField();

    /**
     * 添加数据表主键字段
     */
    void reAddPrimaryKeyField();

}
