package com.huangjiabin.site.sys.service;

import com.huangjiabin.site.sys.model.Activity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-20
 */
public interface ActivityService extends IService<Activity> {
    List<Activity> selectActivityByUserId(Long userId);
}
