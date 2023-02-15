package com.huangjiabin.site.sys.filter;

import com.huangjiabin.site.sys.model.Authority;
import com.huangjiabin.site.sys.model.Role;
import com.huangjiabin.site.sys.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/*
    自定义权限拦截，这里可以根据请求的url判断所需权限
*/
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private AuthorityService authorityService;
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        System.out.println("FilterInvocationSecurityMetadataSource========");
        //获取请求url
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        //通过authorityService匹配permission表中url拿到api访问所需角色也就是菜单角色，我没设置
        List<Authority> authoritys = authorityService.getAuthorityWithRole();
        for(Authority authority :authoritys){
            System.out.println("authority==="+authority);
            //判断请求url是否与权限匹配
            if(antPathMatcher.match(authority.getName(),requestUrl)){
                String[] authorityNames = authority.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(authorityNames);
            }
        }
        //设置角色 return SecurityConfig.createList(角色数组);

        //没有设置角色的菜单就给菜单设置一个ROLE_LOGIN角色，登录才能访问角色
        return SecurityConfig.createList("ROLE_LOGIN");
        //return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
