package com.huangjiabin.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private PasswordEncoder pw;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("进入了loadUserByUsername");
        if(!"admin".equals(username)){
            throw new UsernameNotFoundException("用户名不存在");
        }
        String password=pw.encode("123");
        //ROLE_abc ：abc是角色
        return new User(username,password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_abc,/main.html"));
    }
}
