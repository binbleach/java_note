package com.huangjiabin.springboot.service.impl;

import com.huangjiabin.springboot.service.MyService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

//自定义认证流程，这里是判断权限是否包含uri，是返回ture
@Service
public class MyServiceImpl implements MyService {
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if(principal instanceof UserDetails){
            UserDetails userDetails = (UserDetails)principal;
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            return authorities.contains(new SimpleGrantedAuthority(request.getRequestURI()));
        }
        return false;
    }
}
