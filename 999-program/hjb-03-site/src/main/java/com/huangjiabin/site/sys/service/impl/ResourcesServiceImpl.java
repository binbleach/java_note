package com.huangjiabin.site.sys.service.impl;

import com.huangjiabin.site.sys.model.Resources;
import com.huangjiabin.site.sys.mapper.ResourcesMapper;
import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.service.ReserveService;
import com.huangjiabin.site.sys.service.ResourcesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Service
public class ResourcesServiceImpl extends ServiceImpl<ResourcesMapper, Resources> implements ResourcesService {
    @Resource
    private ResourcesMapper resourcesMapper;

    //检查资源是否可用,可用返回资源
    @Override
    public RespBean checkResourcesById(Long id) {
        Resources resources = resourcesMapper.selectById(id);
        if(resources!=null){
            if(resources.getIsDelete()==1){
                return RespBean.error("资源被删除");
            }
            return RespBean.success("资源可用",resources);
        }
        return RespBean.error("资源不存在");
    }
}
