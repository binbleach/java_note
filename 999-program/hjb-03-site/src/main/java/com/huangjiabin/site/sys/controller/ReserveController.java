package com.huangjiabin.site.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangjiabin.site.sys.model.*;
import com.huangjiabin.site.sys.service.*;
import com.huangjiabin.site.sys.util.EntityUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 预约表 前端控制器
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@RestController
@RequestMapping("/sys/reserve")
public class ReserveController {
    @Autowired
    HandleService handleService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ReserveService reserveService;
    @Autowired
    private ReserveHandleService reserveHandleService;
    @Autowired
    private EmailLogService emailLogService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private PlaceService placeService;

    public static LocalDateTime createTime=LocalDateTime.now();

    @ApiOperation(value = "申请场地或资源")
    @PostMapping("/createReserve")
    public RespBean placeReserve(@RequestBody Map map) {
        Reserve reserve = EntityUtil.mapToBean(map, Reserve.class);
        return reserveService.createReserve(reserve);
    }

    /*
        预定场和活动
    */
    @ApiOperation(value = "申请场地和活动预定")
    @Transactional
    @PutMapping("/placeAndActivityReserve")
    public RespBean placeAndActivityReserve(@RequestBody Map map){
        Reserve reserve = EntityUtil.mapToBean(map, Reserve.class);
        Activity activity = EntityUtil.mapToBean(map, Activity.class);
        return reserveService.createReserveWithActivity(reserve,activity);
    }

    @ApiOperation(value = "通过或拒绝预定操作，45通46拒绝")
    @PutMapping("/reserveHandle")
    public RespBean reserveHandle (@RequestBody Map map){
        Handle handle=EntityUtil.mapToBean(map,Handle.class);
        return handleService.handle(handle);
    }

    @ApiOperation(value = "分页查询所有预定信息")
    @GetMapping("/selectAllReserveForPage/{current}/{size}")
    public RespBean getPlaceReserveForPage(@PathVariable("size") Long size,@PathVariable("current") Long current){
        IPage<Reserve> page  = new Page();
        //设置分页的数据
        page.setCurrent(current);//页码
        page.setSize(size);// 每页的记录数
        IPage<Reserve> result = reserveService.page(page);
        return RespBean.success("true",result);
    }

    @ApiOperation(value = "获取某个场地或资源的所有预定信息")
    @GetMapping(value = "/getOneTargetAllReserve/{targetId}")
    public RespBean getAllPlaceReserve(@PathVariable("targetId") Long targetId){
        QueryWrapper<Reserve> qw = new QueryWrapper<>();
        qw.eq("target_id",targetId);
        List<Reserve> reserveList = reserveService.list(qw);
        return RespBean.success("true",reserveList);
    }

    @ApiOperation(value = "按条件查询申请信息")
    @PostMapping(value = "/selectReserveByCondition")
    public RespBean getSpecialPlaceReserve(@RequestBody Map map){
        QueryWrapper<Reserve> qw = new QueryWrapper<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for(Object key:map.keySet()){
            if("placeId".equals(key)){
                qw.eq("target_id",map.get(key));    //预定目标id
            }else if("reserveStatus".equals(key)){
                qw.eq("reserve_status",map.get(key));   //预定中51 成功52 失败53
            }else if("startTime".equals(key)){          //开始时间
                qw.le("end_time",LocalDateTime.parse((String)map.get(key),df)).or().
                le("start_time",LocalDateTime.parse((String)map.get(key),df));
            }else if("endTime".equals(key)){
                qw.ge("end_time",LocalDateTime.parse((String)map.get(key),df)).or().
                ge("start_time",LocalDateTime.parse((String)map.get(key),df));
            }else if("isCancel".equals(key)){
                qw.eq("is_cancel",map.get(key));
            }
        }
        List<Reserve> reserveList = reserveService.list(qw);
        return RespBean.success("true",reserveList);
    }

    @ApiOperation(value = "删除申请，逻辑删除")
    @DeleteMapping(value = "/deleteReserve/{id}")
    public RespBean deleteReserve(@PathVariable("id") Long id){
        return reserveService.deleteReserveByIdP(id);
    }

    @ApiOperation(value = "取消申请")
    @PostMapping(value = "/cancelReserve/{id}")
    public RespBean cancelReserve(@PathVariable("id") Long id){
        return reserveService.cancelReserveById(id);
    }


}

