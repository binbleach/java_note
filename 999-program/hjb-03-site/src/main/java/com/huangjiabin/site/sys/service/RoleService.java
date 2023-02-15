package com.huangjiabin.site.sys.service;

import com.huangjiabin.site.sys.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
public interface RoleService extends IService<Role> {

    List<Role> selectRoleByUserId(Long id);

    List<Role> selectRoleWithAuthority();

}
