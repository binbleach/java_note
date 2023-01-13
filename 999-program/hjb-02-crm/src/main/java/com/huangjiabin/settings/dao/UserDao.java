package com.huangjiabin.settings.dao;

import com.huangjiabin.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User login(Map<String, String> map);
    List getUserList();

}
