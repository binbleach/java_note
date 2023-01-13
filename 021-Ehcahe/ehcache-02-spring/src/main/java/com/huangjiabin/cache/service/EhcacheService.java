package com.huangjiabin.cache.service;

import com.huangjiabin.cache.entity.User;

public interface EhcacheService {
    User findById(String id);
    String refreshByName(String username);
    void removeById(String id);
}
