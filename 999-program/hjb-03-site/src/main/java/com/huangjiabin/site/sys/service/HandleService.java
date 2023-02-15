package com.huangjiabin.site.sys.service;

import com.huangjiabin.site.sys.model.Handle;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huangjiabin.site.sys.model.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-28
 */
public interface HandleService extends IService<Handle> {
    RespBean handle(Handle Handle);
}
