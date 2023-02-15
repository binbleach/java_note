package com.huangjiabin.site.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huangjiabin.site.sys.mapper.ActivityMapper;
import com.huangjiabin.site.sys.model.Activity;
import com.huangjiabin.site.sys.service.ActivityService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-20
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
    @Resource
    private ActivityMapper activityMapper;
    @Override
    public List<Activity> selectActivityByUserId(Long userId) {
        return activityMapper.selectActivityByUserId(userId);
    }
}
