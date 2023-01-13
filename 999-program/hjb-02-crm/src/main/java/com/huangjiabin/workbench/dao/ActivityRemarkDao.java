package com.huangjiabin.workbench.dao;

import com.huangjiabin.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int selectRemark(String [] id);
    int deleteRemarks(String[] id);
    List<ActivityRemark> getRemarks(String id);
    int deleteRemark (String id);
    int saveRemark(ActivityRemark remark);
    int updateRemark(ActivityRemark activityRemark);
}
