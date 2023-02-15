package com.huangjiabin.site.sys.service;

import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.model.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.security.Principal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
public interface UserService extends IService<User> {
    RespBean checkUserCanCreate(User user);
    RespBean checkUser(Long id);
    RespBean getCurrentLoginUser(Principal principal);
}
