package com.huangjiabin.site.sys.service.impl;

import com.huangjiabin.site.sys.model.DictList;
import com.huangjiabin.site.sys.mapper.DictListMapper;
import com.huangjiabin.site.sys.service.DictListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据类型表 服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-27
 */
@Service
public class DictListServiceImpl extends ServiceImpl<DictListMapper, DictList> implements DictListService {
    @Resource
    private DictListMapper dictListMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<DictList> getAllDictListWithDate() {
        List<DictList> allDictListWithData = (List<DictList>) redisTemplate.opsForValue().get("dict");
        if(CollectionUtils.isEmpty(allDictListWithData)){
            allDictListWithData = dictListMapper.getAllDictList();
            redisTemplate.opsForValue().set("dict",allDictListWithData);
        }
        return allDictListWithData;
    }
}
