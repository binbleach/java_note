package com.huangjiabin.workbench.service;

import com.huangjiabin.workbench.domain.Activity;
import com.huangjiabin.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;


public interface ActivityService {
    int save(Activity activity);

    Map activityPageList(Map map);

    boolean delete (String param[]);

    Map getUpdateInfo(String id);

    boolean update (Activity activity);

    Activity detail(String id);

    List<ActivityRemark> getRemark(String id);

    boolean deleteRemark (String id);

    boolean saveRemark (ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);
}
