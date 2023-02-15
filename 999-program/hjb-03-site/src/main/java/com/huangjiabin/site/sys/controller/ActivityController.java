package com.huangjiabin.site.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangjiabin.site.sys.model.*;
import com.huangjiabin.site.sys.service.ActivityService;
import com.huangjiabin.site.sys.service.HandleService;
import com.huangjiabin.site.sys.service.UserActivityService;
import com.huangjiabin.site.sys.service.UserService;
import com.huangjiabin.site.sys.util.EntityUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-20
 */
@RestController
@RequestMapping("/sys/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;
    @Autowired
    private HandleService handleService;
    @Autowired
    private UserActivityService userActivityService;
    @Autowired
    private UserService userService;

    LocalDateTime currentTime = LocalDateTime.now();

    @ApiOperation(value = "分页查活动信息，逻辑删除查的到")
    @GetMapping("/getActivityPageT/{current}/{size}")
    public RespBean getActivityPageT (@PathVariable("size") Long size, @PathVariable("current") Long current){
        IPage<Activity> page  = new Page();
        //设置分页的数据
        page.setCurrent(current);//页码
        page.setSize(size);// 每页的记录数
        IPage<Activity> result = activityService.page(page);
        return RespBean.success("true",result);
    }

    @ApiOperation(value = "分页查活动信息，逻辑删除查不到")
    @GetMapping("/getActivityPageP/{current}/{size}")
    public RespBean getActivityPage (@PathVariable("size") Long size, @PathVariable("current") Long current){
        QueryWrapper<Activity> qw = new QueryWrapper<>();
        qw.eq("is_delete",0);
        IPage<Activity> page  = new Page();
        //设置分页的数据
        page.setCurrent(current);//页码
        page.setSize(size);// 每页的记录数
        IPage<Activity> result = activityService.page(page,qw);
        return RespBean.success("true",result);
    }

    @ApiOperation(value = "活动id查活动信息")
    @GetMapping("/getActivityById/{id}")
    public RespBean getActivityById (@PathVariable("id") Long id){
        Activity activity = activityService.getById(id);
        return RespBean.success("true", activity);
    }

    @ApiOperation(value = "通过预定id查活动信息")
    @GetMapping("/getActivityByReserveId/{reserveId}")
    public RespBean getActivityByReserveId (@PathVariable("reserveId") Long reserveId){
        QueryWrapper<Activity> activityQueryWrapper = new QueryWrapper<>();
        activityQueryWrapper.eq("reserve_id",reserveId);
        Activity activity = activityService.getOne(activityQueryWrapper);
        return RespBean.success("true", activity);
    }
    @ApiOperation(value = "停止活动")
    @PutMapping("/stopActivity")
    public RespBean stopActivity (@RequestBody Map map){
        Boolean result = false;
        Boolean result2 = false;
        Handle handle=null;
        LocalDateTime createTime = LocalDateTime.now();
        try {
            UpdateWrapper<Activity> uw = new UpdateWrapper<>();
            uw.set("is_stop",1);
            uw.eq("id",map.get("targetId"));
            result = activityService.update(uw);
            if(result){
//                handle=EntityUtil.mapToBean(map,Handle.class);
//                handle.setHandleResult(47);
//                handle.setHandleTarget("sys_activity");
//                handle.setTime(createTime);
//                handle.setIsEmail(0);
//                handle.setIsEmailSuccess(0);
//                result2 = handleService.save(handle);
            }
            if(result2){
                return RespBean.success("取消成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("取消失败");
    }
    @ApiOperation(value = "参加活动")
    @PutMapping("/joinActivity")
    public RespBean joinActivity (@RequestBody Map map){
        UserActivity userActivity;
        try {
            //判断是否是退出活动
            QueryWrapper<UserActivity> qw = new QueryWrapper<>();
            qw.eq("user_id",map.get("userId"));
            qw.eq("activity_id",map.get("activityId"));
            qw.eq("is_out",1);
            userActivity = userActivityService.getOne(qw);
            //是退出活动修改退出
            if(userActivity!=null){
                UpdateWrapper<UserActivity> uwUserActivity = new UpdateWrapper<>();
                uwUserActivity.set("is_out",0);
                uwUserActivity.eq("user_id",map.get("userId"));
                uwUserActivity.eq("activity_id",map.get("activityId"));
                boolean update = userActivityService.update(uwUserActivity);
                if(update){
                    UpdateWrapper<Activity> uwActivity = new UpdateWrapper<>();
                    uwActivity.setSql("join_num=join_num+1");
                    uwActivity.eq("id",userActivity.getActivityId());
                    activityService.update(uwActivity);
                    return RespBean.success("重新参加成功");
                }
            }else {
                //不是退出的活动，参加，或者已经参加了就参加失败
                userActivity = EntityUtil.mapToBean(map,UserActivity.class);
                userActivity.setJoinTime(currentTime);
                userActivity.setIsOut(0);
                boolean save = userActivityService.save(userActivity);
                if(save){
                    UpdateWrapper<Activity> uw = new UpdateWrapper<>();
                    uw.setSql("join_num=join_num+1");
                    uw.eq("id",userActivity.getActivityId());
                    activityService.update(uw);
                    return RespBean.success("参加成功");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("参加失败");
    }
    @ApiOperation(value = "退出活动B")
    @PutMapping("/outActivity")
    public RespBean outActivity (@RequestBody Map map){
        LocalDateTime outTime = LocalDateTime.now();
        try {
           UpdateWrapper<UserActivity> uw = new UpdateWrapper<>();
           uw.set("out_time",outTime);
           uw.set("is_out",1);
           uw.eq("user_id",map.get("userId"));
           uw.eq("activity_id",map.get("activityId"));
           boolean update = userActivityService.update(uw);
           //更改成功则退出成功
           if(update){
               UpdateWrapper<Activity> uw2= new UpdateWrapper<>();
               uw2.setSql("join_num=join_num-1");
               uw2.eq("id",map.get("activityId"));
               activityService.update(uw2);
               return RespBean.success("退出成功");
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("退出失败");
    }
    @ApiOperation(value = "删除活动B逻辑删除")
    @DeleteMapping("/deleteActivityP/{id}")
    public RespBean deleteActivityP (@PathVariable("id")Long id){
        Boolean result;
        try {
            UpdateWrapper<Activity> uw = new UpdateWrapper<>();
            uw.set("is_delete",1);
            uw.eq("id",id);
            result = activityService.update(uw);
            if(result){
                return RespBean.success("删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("删除失败");
    }
    @ApiOperation(value = "删除活动B物理删除")
    @DeleteMapping("/deleteActivityT/{id}")
    public RespBean deleteActivityT (@PathVariable("id")Long id){
        Boolean result;
        try {
            result = activityService.removeById(id);
            if(result){
                return RespBean.success("删除成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("删除失败");
    }
    @ApiOperation(value = "查找用户参加的活动B")
    @GetMapping("/getActivityyUserId/{userId}")
    public RespBean getUserJoinActivity (@PathVariable("userId")Long userId){
        try {
            List<Activity> activityList = activityService.selectActivityByUserId(userId);
            if(!CollectionUtils.isEmpty(activityList)){
                return RespBean.success("查询成功", activityList);
            }else {
                return RespBean.success("用户没有参加的活动");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("查询失败");
    }

}

