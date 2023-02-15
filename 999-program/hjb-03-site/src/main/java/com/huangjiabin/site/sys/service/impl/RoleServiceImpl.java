package com.huangjiabin.site.sys.service.impl;

import com.huangjiabin.site.sys.model.Role;
import com.huangjiabin.site.sys.mapper.RoleMapper;
import com.huangjiabin.site.sys.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> selectRoleByUserId(Long id) {
         return roleMapper.selectRoleByUserId(id);
    }

    @Override
    public List<Role> selectRoleWithAuthority() {
        return roleMapper.selectRoleWithAuthority();
    }
}
