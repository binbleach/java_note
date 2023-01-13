package com.huangjiabin.workbench.service.impl;

import com.huangjiabin.settings.dao.UserDao;
import com.huangjiabin.settings.domain.User;
import com.huangjiabin.settings.service.UserService;
import com.huangjiabin.utils.SqlSessionUtil;
import com.huangjiabin.workbench.dao.ActivityDao;
import com.huangjiabin.workbench.dao.ActivityRemarkDao;
import com.huangjiabin.workbench.domain.Activity;
import com.huangjiabin.workbench.domain.ActivityRemark;
import com.huangjiabin.workbench.service.ActivityService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {
    private ActivityDao  activityDao= SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);


    @Override
    public int save(Activity activity) {
        int flag =0;
        flag=activityDao.save(activity);
        return flag;
    }

    @Override
    public Map activityPageList(Map map) {
        List list=activityDao.getActivityListByCondition(map);
        int total = activityDao.getTotal();
        Map map2 = new HashMap();
        map2.put("list",list);
        map2.put("total",total);
        return map2;
    }

    @Override
    public boolean delete(String[] param) {
        int num=activityRemarkDao.selectRemark(param);
        int num1 = activityRemarkDao.deleteRemarks(param);
        if(num!=num1){
            return false;
        }
        int num2=activityDao.deleteActivity(param);
        if(param.length!=num2){
            return false;
        }
        return true;
    }

    @Override
    public Map getUpdateInfo(String id) {
        Map map = new HashMap();
        Activity activity=activityDao.getUpdateInfo(id);
        List<User> list=userDao.getUserList();

        map.put("list",list);
        map.put("info",activity);
        return map;
    }

    @Override
    public boolean update(Activity activity) {
        int num=activityDao.updateActivity(activity);
        if(num==1){
            return true;
        }
        return false;
    }

    @Override
    public Activity detail(String id) {
        Activity activity = activityDao.getUpdateInfo(id);
        return activity;
    }

    public List<ActivityRemark> getRemark(String id){
        List<ActivityRemark> list=activityRemarkDao.getRemarks(id);
        return list;
    }

    @Override
    public boolean deleteRemark(String id) {
        int num=activityRemarkDao.deleteRemark(id);
        if(num==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {
        int num=activityRemarkDao.saveRemark(activityRemark);
        if(num==1){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        int num=activityRemarkDao.updateRemark(activityRemark);
        if(num==1){
            return true;
        }
        return false;
    }


}
