package com.lyhui.mapper;

import com.lyhui.pojo.User;

public interface UserMapper {

    int delete(String name);

    User query(User user);
}
