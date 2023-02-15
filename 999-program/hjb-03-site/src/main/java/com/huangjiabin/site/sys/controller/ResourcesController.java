package com.huangjiabin.site.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangjiabin.site.sys.model.*;
import com.huangjiabin.site.sys.service.FilesService;
import com.huangjiabin.site.sys.service.ResourcesFilesService;
import com.huangjiabin.site.sys.service.ResourcesService;
import com.huangjiabin.site.sys.util.EntityUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 资源表 前端控制器
 * </p>
 *
 * @author huangjiabin
 * @since 2022-02-28
 */
@RestController
@RequestMapping("/sys/resources")
public class ResourcesController {
    @Autowired
    private ResourcesService resourcesService;
    @Autowired
    private FilesService filesService;

    @Autowired
    private ResourcesFilesService resourcesFilesService;

//    @ApiOperation(value = "创建资源，并返回资源信息")
//    @PostMapping("/createResources")
//    public RespBean createResources(@RequestBody Map<String,Object> map){
//        Resources resources;
//        Files files = null;
//        ResourcesFiles resourcesFiles = new ResourcesFiles();
//        boolean result1 = false;
//        boolean result2 = false;
//        boolean result3 =false;
//        Map<String,Object> resultMap = new HashMap();
//        try {
//            resources = EntityUtil.mapToBean((Map)map.get("resources"), Resources.class);
//             result1 = resourcesService.save(resources);
//             resultMap.put("resources",resources);
//            if(map.get("files")!=null){
//                files = EntityUtil.mapToBean((Map)map.get("files"),Files.class);
//                 result2 = filesService.save(files);
//                resultMap.put("files",files);
//            }
//            if(result1&&result2){
//                resourcesFiles.setFilesId(files.getId());
//                resourcesFiles.setResourcesId(resources.getId());
//                resourcesFilesService.save(resourcesFiles);
//            }
//            if(result1){
//                return RespBean.success("创建成功",resultMap);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return RespBean.error("资源创建失败");
//    }
    @ApiOperation(value = "创建资源，并返回资源信息")
    @PostMapping("/createResources")
    public RespBean createResources(@RequestBody Map map){
        try {
            Resources resources=EntityUtil.mapToBean(map, Resources.class);
            boolean result = resourcesService.save(resources);
            if(result){
                return RespBean.success("场地创建成功",resources);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("资源创建失败");
    }
    @ApiOperation(value = "修改资源信息")
    @PutMapping("/updateResources")
    public RespBean updateResources(@RequestBody Resources resources){
        boolean result = resourcesService.updateById(resources);
        if(result){
           return RespBean.success("更改成功",resources);
        }
        return RespBean.error("更改失败");
    }
    @ApiOperation(value = "分页获取资源信息")
    @GetMapping("/getResourcesPage/{current}/{size}")
    public RespBean getResourcesPage(@PathVariable("size") Long size,@PathVariable("current") Long current){
        QueryWrapper<Resources> qw = new QueryWrapper<>();
        qw.eq("is_delete",0);
        IPage<Resources> page  = new Page();
        //设置分页的数据
        page.setCurrent(current);//页码
        page.setSize(size);// 每页的记录数
        IPage<Resources> result = resourcesService.page(page,qw);
        return RespBean.success("true",result);
    }
    @ApiOperation(value = "删除资源信息，逻辑删除")
    @DeleteMapping("/deleteResourcesPById/{id}")
    public RespBean deleteResourcesPById(@PathVariable("id") Long id){
        UpdateWrapper<Resources> uw = new UpdateWrapper<>();
        uw.set("is_delete",1);
        uw.eq("id",id);
        boolean update = resourcesService.update(uw);
        if(update){
            return RespBean.success("删除成功");
        }else {
            return RespBean.error("删除失败");
        }
    }
    @ApiOperation(value = "删除资源信息，物理删除")
    @DeleteMapping("/deleteResourcesTById/{id}")
    public RespBean deleteResourcesTById(@PathVariable("id") Long id){
        boolean delete = resourcesService.removeById(id);
        if(delete){
            return RespBean.success("删除成功");
        }else {
            return RespBean.error("删除失败");
        }
    }


}

