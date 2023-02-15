package com.huangjiabin.site.sys.service.impl;

import com.huangjiabin.site.sys.model.UserActivity;
import com.huangjiabin.site.sys.mapper.UserActivityMapper;
import com.huangjiabin.site.sys.service.UserActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 活动参与表 服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-28
 */
@Service
public class UserActivityServiceImpl extends ServiceImpl<UserActivityMapper, UserActivity> implements UserActivityService {

}
