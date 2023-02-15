package com.huangjiabin.site.sys.mapper;

import com.huangjiabin.site.sys.model.Reserve;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 预约表 Mapper 接口
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
public interface ReserveMapper extends BaseMapper<Reserve> {
    List<Reserve> selectNotConflict(Reserve reserve);
}
