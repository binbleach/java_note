package com.huangjiabin.mimissm.service;

import com.huangjiabin.mimissm.pojo.Admin;

public interface AdminService {
    //完成登录判断
    Admin login(String name, String pwd);
}
