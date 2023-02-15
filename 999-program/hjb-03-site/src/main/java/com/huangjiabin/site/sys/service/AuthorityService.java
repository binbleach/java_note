package com.huangjiabin.site.sys.service;

import com.huangjiabin.site.sys.model.Authority;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
public interface AuthorityService extends IService<Authority> {

    List<Authority> getAuthorityByUserId();

    List<Authority> getAuthorityWithRole();

}
