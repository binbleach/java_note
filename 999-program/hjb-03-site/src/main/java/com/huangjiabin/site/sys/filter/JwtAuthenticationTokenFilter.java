package com.huangjiabin.site.sys.filter;

import com.huangjiabin.site.sys.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Jwt登录授权过滤器
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JwtAuthenticationTokenFilter==========");
        String captcha = (String) request.getSession().getAttribute("captcha");
        System.out.println("拦截器拦截的验证码============"+captcha);
        //请求头中获取Authorization值
        String autHeader=request.getHeader(tokenHeader);
        //存在token，先判断是否存在Authorization值，值是否以Bearer开头
        if(null!=autHeader && autHeader.startsWith(tokenHead)){
            //从Authorization值中获取token
            String authToken = autHeader.substring(tokenHead.length());
            //从token中获取username
            String username = null;
            try {
                 username=jwtTokenUtil.getUserNameByToken(authToken);   
            }catch (ExpiredJwtException e){
                
            }
            //token存在用户名，但是未登录，比如登录过但是我重启了服务器，比如第二次发起请求
            if(null!=username&& null==SecurityContextHolder.getContext().getAuthentication()){
                //登录
                UserDetails userDetails=userDetailsService.loadUserByUsername(username);
                //验证token是否生效,验证用户名和有效时间，重新设置用户对象
                if(jwtTokenUtil.verifyToken(userDetails,authToken)){
                    jwtTokenUtil.refreshToken(authToken);
                    //存在线程池里，请求结束则无
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                            UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
