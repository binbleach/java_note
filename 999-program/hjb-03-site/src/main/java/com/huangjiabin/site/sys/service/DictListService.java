package com.huangjiabin.site.sys.service;

import com.huangjiabin.site.sys.model.DictList;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据类型表 服务类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-27
 */
public interface DictListService extends IService<DictList> {
    List<DictList> getAllDictListWithDate();
}
