package com.huangjiabin.workbench.dao;

import com.huangjiabin.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity activity);
    List getActivityListByCondition(Map map);
    int getTotal();
    int deleteActivity(String [] id);
    Activity getUpdateInfo(String id);
    int updateActivity(Activity activity);
}
