package com.huangjiabin.settings.service;

import com.huangjiabin.exception.LoginException;
import com.huangjiabin.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User login(String loginAct,String loginPwd,String ip) throws LoginException;
    List getUserList();
}
