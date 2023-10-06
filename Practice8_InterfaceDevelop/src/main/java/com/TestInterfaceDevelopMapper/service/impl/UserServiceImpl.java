package com.TestInterfaceDevelopMapper.service.impl;

import com.TestInterfaceDevelopMapper.mapper.UserMapper;
import com.TestInterfaceDevelopMapper.model.User;
import com.TestInterfaceDevelopMapper.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 标识为服务类
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer login(User user) {
        return userMapper.login(user);
    }

    @Override
    public PageInfo<User> selectAll(User user, int page, int size) {
        //开启PageHelper的分页
        PageHelper.startPage(page,size);
        //获取分页数据信息
        List<User> list = userMapper.getAllUser(user);
        //生成PageInfo对象
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Integer deleteUser(int userId) {
        return userMapper.deleteUser(userId);
    }

    @Override
    public Integer insertUser(User insert) {
        return userMapper.addUser(insert);
    }

    @Override
    public void deletePrimaryKeyField() {
        userMapper.delPrimaryKeyField();
    }

    @Override
    public void reAddPrimaryKeyField() {
        userMapper.reAddPrimaryKeyField();
    }

    @Override
    public Integer updateUser(User user) {
        return userMapper.updateUserInfo(user);
    }

}
