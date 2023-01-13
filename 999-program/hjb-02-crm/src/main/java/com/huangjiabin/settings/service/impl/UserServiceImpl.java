package com.huangjiabin.settings.service.impl;

import com.huangjiabin.exception.LoginException;
import com.huangjiabin.settings.dao.UserDao;
import com.huangjiabin.settings.domain.User;
import com.huangjiabin.settings.service.UserService;
import com.huangjiabin.utils.DateTimeUtil;
import com.huangjiabin.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao=SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        Map<String,String> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user=userDao.login(map);
        if(user==null){
            throw new LoginException("账号或密码错误");
        }
        /*if(!user.getAllowIps().contains(ip)){
            throw new LoginException("ip地址受限请联系管理员");
        }*/
        String currentTimeMillis= DateTimeUtil.getSysTime();
        if(user.getExpireTime().compareTo(currentTimeMillis)>0){
            throw new LoginException("账号失效");
        }
        if("0".equals(user.getLockState())){
            throw new LoginException("账号被锁定");
        }
        return user;
    }

    @Override
    public List getUserList() {
        List list=userDao.getUserList();
        return list;
    }


}
