package com.huangjiabin.site.sys.mapper;

import com.huangjiabin.site.sys.model.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectRoleByUserId(Long id);

    List<Role> selectRoleWithAuthority();
}
