package com.huangjiabin.site.sys.service.impl;

import com.huangjiabin.site.sys.model.Place;
import com.huangjiabin.site.sys.mapper.PlaceMapper;
import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.service.PlaceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huangjiabin.site.sys.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 场地信息表 服务实现类
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@Service
public class PlaceServiceImpl extends ServiceImpl<PlaceMapper, Place> implements PlaceService {
    @Resource
    private PlaceMapper placeMapper;

    //删除场地，逻辑删除
    @Override
    public Boolean deletePlaceByIdP(Long id) {

        return false;
    }

    //检查场地是否可用，可用返回场地
    @Override
    public RespBean checkPlaceById(Long id) {
        Place place = placeMapper.selectById(id);
        if(place!=null){
            if(place.getDelFlag()==1){
                return RespBean.error("场地被删除");
            }
            if(place.getDisable()==1){
                return RespBean.error("场地被禁用");
            }
            return RespBean.success("场地可用",place);
        }
        return RespBean.error("场地为空");
    }
}
