package com.huangjiabin.service.impl;

import com.huangjiabin.domain.User;
import com.huangjiabin.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUser(Integer id, String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }
}
