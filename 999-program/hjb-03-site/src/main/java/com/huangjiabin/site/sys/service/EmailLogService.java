package com.huangjiabin.site.sys.service;

import com.huangjiabin.site.sys.model.EmailLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huangjiabin.site.sys.model.Reserve;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-26
 */
public interface EmailLogService extends IService<EmailLog> {
    Boolean sendEmail(Reserve reserve);
    //delay延迟时间，毫秒
    Boolean sendTTLEmail(Reserve reserve,Integer delay);
}
