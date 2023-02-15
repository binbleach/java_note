package com.huangjiabin.site.sys.service;

import com.huangjiabin.site.sys.model.Place;
import com.baomidou.mybatisplus.extension.service.IService;
import com.huangjiabin.site.sys.model.RespBean;

/**
 * <p>
 * 场地信息表 服务类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
public interface PlaceService extends IService<Place> {
    Boolean deletePlaceByIdP(Long id);

    RespBean checkPlaceById(Long id);
}
