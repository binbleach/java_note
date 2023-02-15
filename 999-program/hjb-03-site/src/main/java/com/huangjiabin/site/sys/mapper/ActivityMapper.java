package com.huangjiabin.site.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huangjiabin.site.sys.model.Activity;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-20
 */
public interface ActivityMapper extends BaseMapper<Activity> {
    List<Activity> selectActivityByUserId(Long userId);
}
