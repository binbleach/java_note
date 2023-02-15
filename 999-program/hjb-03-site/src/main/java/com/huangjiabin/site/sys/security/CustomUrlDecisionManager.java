package com.huangjiabin.site.sys.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
/*
ConfigAttribute：
AbstractSecurityInterceptor 的 beforeInvocation()方法内部在进行鉴权的时候使用的是注入的
AccessDecisionManager 的 decide() 方法进行的。如前所述，decide()方法是需要接收一个受保护对象
对应的ConfigAttribute 集合的。一个 ConfigAttribute 可能只是一个简单的角色名称，具体将视
AccessDecisionManager 的实现者而定。AbstractSecurityInterceptor 将使用一个
SecurityMetadataSource 对象来获取与受保护对象关联的 ConfigAttribute 集合，具体
SecurityMetadataSource 将由子类实现提供。ConfigAttribute 将通过注解的形式定义在受保护的方法
上，或者通过 access 属性定义在受保护的 URL 上。例如我们常见的 就表示将 ConfigAttribute
ROLE_USER和 ROLE_ADMIN 应用在所有的 URL 请求上。对于默认的 AccessDecisionManager 的实现，
上述配置意味着用户所拥有的权限中只要拥有一个 GrantedAuthority 与这两个 ConfigAttribute 中的一个进行匹配
则允许进行访问。当然，严格的来说 ConfigAttribute 只是一个简单的配置属性而已，
具体的解释将由 AccessDecisionManager 来决定。
*/

/*  1、AbstractSecurityInterceptor SpringSecurity中的父亲
    2、1Manager授权接口，AuthenticationManager认证接口
    3、AccessDecisionVoter投票器的父接口(默认实现类WebExpressionVoter)有三个属性1 表示赞成；0 表示弃权；-1 表示拒绝
    4、表决机制：
       AffirmativeBased：有一个投票器同意了，就通过。
       ConsensusBased：多数投票器同意就通过，平局的话，则看 allowIfEqualGrantedDeniedDecisions 参数的取值。
       UnanimousBased 所有投票器都同意，请求才通过。
    5、UsernamePasswordAuthenticationFilter掉AuthenticationManager去认证，认证成功会存
      一个Authentication的实现类UsernamePasswordAuthenticationToken到SecurityContextHolder
    6、未被认证的请求，也就是SecurityContextHolder.getContext().getAuthentication() == null的请求
      AnonymousAuthenticationFilter过滤器会创建AnonymousAuthenticationToken匿名Authentication,
      放入也就是SecurityContextHolder，AnonymousAuthenticationToken里面的isAuthenticated默认为空。
    7、FilterSecurityInterceptor掉父类（AbstractSecurityInterceptor）super.beforeInvocation(FilterInvocation)
      AbstractSecurityInterceptor会先掉authentication.isAuthenticated()判断请求是否被认证，
      如果不是会再掉AuthenticationManager.authenticate进行认证，这步前面UsernamePasswordAuthenticationFilter已经做过。
      AbstractSecurityInterceptor掉(默认AffirmativeBased)AccessDecisionManager.decide()进行授权判断，
      然后会从this.getDecisionVoters选出投票器WebExpressionVoter,掉voter.vote()返回-1则投票+1
      如果投票大于0则抛异常AccessDeniedException(无权限异常)，最后被转换成AuthenticationEntryPoint(未登录异常)

*/
/*
    AffirmativeBased决策器的decide方法
    int deny = 0;
        Iterator var5 = this.getDecisionVoters().iterator();

        while(var5.hasNext()) {
            AccessDecisionVoter voter = (AccessDecisionVoter)var5.next();
            int result = voter.vote(authentication, object, configAttributes);
            switch(result) {
            case -1:
                ++deny;
                break;
            case 1:
                return;
            }
        }

        if (deny > 0) {
            throw new AccessDeniedException(this.messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
        } else {
            this.checkAllowIfAllAbstainDecisions();
        }
*/
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        System.out.println("AccessDecisionManager============");
//        for(ConfigAttribute configAttribute:collection){
//            String needRole = configAttribute.getAttribute();
//        }
        System.out.println("collection=========="+collection);
        for(ConfigAttribute configAttribute:collection){
            //api所需要的角色
            String needRole = configAttribute.getAttribute();
            System.out.println("AccessDecisionManager2============");
            System.out.println("needRole============"+needRole);

            //判断api是不是没配角色
            if("ROLE_LOGIN".equals(needRole)){
                //判断authentication是否是AnonymousAuthenticationToken匿名访问(表示还没登录)，是的话让他登录再访问
                if(authentication instanceof AnonymousAuthenticationToken){
                    //没配角色但是还没登录，让他登录
                    throw new AccessDeniedException("尚未登录请登录！");
                }else {
                    //都没配直接放行
                    return;
                }
            }
            //判断用户角色是否为api所需角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for(GrantedAuthority authority:authorities){
                if(authority.getAuthority().equals(needRole)){
                    return;
                }
            }
            throw new AccessDeniedException("权限不足，请联系管理员");
        }

    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
