package com.huangjiabin.site.sys.service.impl;

import com.huangjiabin.site.sys.model.LoginLog;
import com.huangjiabin.site.sys.mapper.LoginLogMapper;
import com.huangjiabin.site.sys.service.LoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录信息表 服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

}
