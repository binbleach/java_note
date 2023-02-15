package com.huangjiabin.site.sys.service;

import com.huangjiabin.site.sys.model.Resources;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huangjiabin.site.sys.model.RespBean;

import javax.annotation.Resource;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
public interface ResourcesService extends IService<Resources> {
    RespBean checkResourcesById(Long id);
}
