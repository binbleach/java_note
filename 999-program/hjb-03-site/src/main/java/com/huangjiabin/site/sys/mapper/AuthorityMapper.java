package com.huangjiabin.site.sys.mapper;

import com.huangjiabin.site.sys.model.Authority;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
public interface AuthorityMapper extends BaseMapper<Authority> {

    List<Authority> getAuthorityWithRole();

    List<Authority> getAuthorityByUserId(Long userId);
}
