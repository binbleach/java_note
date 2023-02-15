package com.huangjiabin.site.sys.mapper;

import com.huangjiabin.site.sys.model.DictList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 数据类型表 Mapper 接口
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-27
 */
public interface DictListMapper extends BaseMapper<DictList> {
    List<DictList> getAllDictList();
}
