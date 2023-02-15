package com.huangjiabin.site.sys.service.impl;

import com.huangjiabin.site.sys.model.Authority;
import com.huangjiabin.site.sys.mapper.AuthorityMapper;
import com.huangjiabin.site.sys.model.User;
import com.huangjiabin.site.sys.service.AuthorityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {
    @Autowired
    RedisTemplate redisTemplate;
    @Resource
    private AuthorityMapper authorityMapper;
    @Override
    public List<Authority> getAuthorityByUserId() {
       Long userId = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
       ValueOperations<String,Object> valueOperations=redisTemplate.opsForValue();
        List<Authority> authorities = (List<Authority>)valueOperations.get("authority_" + userId);
        if(CollectionUtils.isEmpty(authorities)){
        authorities = authorityMapper.getAuthorityByUserId(userId);
            //将数据存到redis
            valueOperations.set("authority_"+userId,authorities);
        }
       return authorities;
    }

    @Override
    public List<Authority> getAuthorityWithRole() {

        return authorityMapper.getAuthorityWithRole();
    }


}
