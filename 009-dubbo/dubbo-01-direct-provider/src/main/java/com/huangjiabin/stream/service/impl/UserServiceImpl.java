package com.huangjiabin.stream.service.impl;

import com.huangjiabin.domain.User;
import com.huangjiabin.stream.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User getUserById() {
        User user = new User();
        user.setId(1234);
        user.setUsername("徐凤年");
        user.setAge(30);
        return user;
    }
}
