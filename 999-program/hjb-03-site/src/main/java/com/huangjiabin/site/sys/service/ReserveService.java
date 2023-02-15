package com.huangjiabin.site.sys.service;

import com.huangjiabin.site.sys.model.*;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 * 预约表 服务类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
public interface ReserveService extends IService<Reserve> {
    Map getPlaceReserve(Long placeId);

    PageInfo<Reserve> getAllReserveForPage(Integer pageNum, Integer pageSize);

    RespBean isCanReserve(Reserve reserve);

    RespBean deleteReserveByIdP(Long id);

    RespBean cancelReserveById(Long id);

    RespBean createReserve(Reserve reserve);

    RespBean createReserveWithActivity(Reserve reserve,Activity activity);

    RespBean createReserveActivity(Activity activity);



}
