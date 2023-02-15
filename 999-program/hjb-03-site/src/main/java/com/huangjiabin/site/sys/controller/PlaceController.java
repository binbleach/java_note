package com.huangjiabin.site.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangjiabin.site.sys.model.Place;
import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.model.User;
import com.huangjiabin.site.sys.service.PlaceService;
import com.huangjiabin.site.sys.util.EntityUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 场地信息表 前端控制器
 * </p>
 *
 * @author huangjiabin
 * @since 2022-02-27
 */
@RestController
@RequestMapping("/sys/place")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @ApiOperation(value="创建场地")
    @PostMapping("/createPlace")
    public RespBean createPlace (@RequestBody Map map, HttpServletRequest request){
        try {
            Place place=EntityUtil.mapToBean(map, Place.class);
            boolean result = placeService.save(place);
            if(result){
                return RespBean.success("场地创建成功",place);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("场地创建失败");
    }
    @ApiOperation(value="分页查询场地")
    @GetMapping("/getPlacePage/{current}/{size}")
    public RespBean getPlacePage(@PathVariable("size") Long size,@PathVariable("current") Long current){
        QueryWrapper<Place> qw = new QueryWrapper<>();
        qw.eq("del_flag",0);
        IPage<Place> page  = new Page();
        //设置分页的数据
        page.setCurrent(current);//页码
        page.setSize(size);// 每页的记录数
        IPage<Place> result = placeService.page(page,qw);
        return RespBean.success("true",result);
    }
    @ApiOperation(value="修改场所信息")
    @PutMapping("/updatePlace")
    public RespBean updatePlace(@RequestBody Place place){
        boolean result = placeService.updateById(place);
        if(result){
            return RespBean.success("更改成功",place);
        }
        return RespBean.error("更改失败");
    }
    @ApiOperation(value="根据id删除场地信息,逻辑删除")
    @DeleteMapping("/deletePlaceById/{id}")
    public RespBean deletePlaceById(@PathVariable("id") Long id){
        UpdateWrapper<Place> qw= new UpdateWrapper<>();
        qw.eq("id",id).set("del_flag",1);
        boolean result = placeService.update(qw);

        if(result){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

}

