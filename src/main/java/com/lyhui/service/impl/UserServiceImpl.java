package com.lyhui.service.impl;

import com.lyhui.mapper.UserMapper;
import com.lyhui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lyhui.pojo.User;

@Service("userService")
public class UserServiceImpl implements UserService {

    //注入mapper
    @Autowired
    private UserMapper userMapper;

    @Override
    public User get(User user) {
        //提前检查
        if (user == null){
            return null;
        }
        User u = userMapper.query(user);
        System.out.println(u);
        return u;
    }

    @Override
    public boolean delete(String username) {
        int row = userMapper.delete(username);

        return row==1?true:false;

    }
}
