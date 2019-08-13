package com.lyhui.service;

import com.lyhui.pojo.User;

public interface UserService {

    /**
     * 根据user信息去检查数据库是否存在用户
     * @param user
     * @return
     */
    User get(User user);

    boolean delete(String name);
}
