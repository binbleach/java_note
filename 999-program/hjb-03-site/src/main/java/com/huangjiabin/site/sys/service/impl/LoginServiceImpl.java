package com.huangjiabin.site.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.model.Role;
import com.huangjiabin.site.sys.model.Student;
import com.huangjiabin.site.sys.model.User;
import com.huangjiabin.site.sys.service.LoginService;
import com.huangjiabin.site.sys.util.IpUtil;
import com.huangjiabin.site.sys.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @Override
    public RespBean login(String username, String password,String code, HttpServletRequest request) {
        /*String captcha=(String)request.getSession().getAttribute("captcha");
        if(!captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码输入错误！！！");
        }*/
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(null==userDetails || !passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("用户名或密码不正确");
        }
        if(!userDetails.isEnabled()){
            return RespBean.error("账户被禁用，请联系管理员！");
        }
        String ip=IpUtil.getIpAddr(request);
        String info = IpUtil.getIpInfo(ip);
        System.out.println(ip);
        System.out.println(info);
        //验证通过后直接把usernamePasswordAuthenticationToken对象放到security全局里后面拦截器判断是否过认证需要
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new
                UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        //让前端放在请求头里返回
        tokenMap.put("tokenHead",tokenHead);


        return RespBean.success("登录成功",tokenMap);
    }


}
