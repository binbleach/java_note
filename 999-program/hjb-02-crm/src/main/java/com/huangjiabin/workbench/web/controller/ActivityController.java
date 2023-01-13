package com.huangjiabin.workbench.web.controller;

import com.huangjiabin.settings.domain.User;
import com.huangjiabin.settings.service.UserService;
import com.huangjiabin.settings.service.impl.UserServiceImpl;
import com.huangjiabin.utils.DateTimeUtil;
import com.huangjiabin.utils.PrintJson;
import com.huangjiabin.utils.UUIDUtil;
import com.huangjiabin.workbench.domain.Activity;
import com.huangjiabin.workbench.domain.ActivityRemark;
import com.huangjiabin.workbench.domain.PaginationVo;
import com.huangjiabin.workbench.service.ActivityService;
import com.huangjiabin.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path=req.getServletPath();
        if(path.equals("/workbench/activity/getUserList.do")){
            getUserList(req,resp);
        }else if("/workbench/activity/save.do".equals(path)){
            int flag=save(req);
            if(flag==0){
                PrintJson.printJsonFlag(resp,false);
            }else {
                PrintJson.printJsonFlag(resp,true);
            }

        }else if("/workbench/activity/pageActivityList.do".equals(path)){
            activityPageList(req,resp);
        }else if("/workbench/activity/delete.do".equals(path)){
            delete(req,resp);
        }else if ("/workbench/activity/getUpdateInfo.do".equals(path)){
            getUpdateInfo(req,resp);
        }else if("/workbench/activity/update.do".equals(path)){
            update(req,resp);
        }else if("/workbench/activity/detail.do".equals(path)){
            detail(req,resp);
        }else if("/workbench/activity/getRemark.do".equals(path)){
            getRemark(req,resp);
        }else if("/workbench/activity/deleteRemark.do".equals(path)){
            deleteRemark(req,resp);
        }else if("/workbench/activity/saveRemark.do".equals(path)){
            saveRemark(req,resp);
        }else if("/workbench/activity/updateRemark.do".equals(path)){
            updateRemark(req,resp);
        }

    }


    private void getUserList(HttpServletRequest req, HttpServletResponse resp){
        UserService us = new UserServiceImpl();
        List list=us.getUserList();
        PrintJson.printJsonObj(resp,list);
    }
    private int save(HttpServletRequest request){
        System.out.println("save进来了喔");
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        ActivityService activityService = new ActivityServiceImpl();
        int flag=activityService.save(activity);
        return flag;
    }
    private void activityPageList(HttpServletRequest request, HttpServletResponse response){
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        int pageSize = Integer.valueOf(request.getParameter("pageSize"));
        int pageNo = Integer.valueOf(request.getParameter("pageNo"));

        int skipCount=(pageNo-1)*pageSize;

        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("pageSize",pageSize);
        map.put("skipCount",skipCount);


        PaginationVo vo = new PaginationVo();
        ActivityService activityService = new ActivityServiceImpl();
        map=activityService.activityPageList(map);
        vo.setList((List) map.get("list"));
        vo.setTotal((int)map.get("total"));
        PrintJson.printJsonObj(response,vo);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {
        String [] params=req.getParameterValues("id");
        ActivityService service = new ActivityServiceImpl();
        Boolean flag=service.delete(params);
        System.out.println(flag);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void update(HttpServletRequest request, HttpServletResponse response) {
        String id=request.getParameter("id");
        String  owner = request.getParameter("owner");
        String  name = request.getParameter("name");
        String  startDate = request.getParameter("startDate");
        String  endDate = request.getParameter("endDate");
        String  cost = request.getParameter("cost");
        String  description = request.getParameter("description");
        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);

        ActivityService service = new ActivityServiceImpl();
        service.update(activity);
        PrintJson.printJsonFlag(response,true);
    }
    private void getUpdateInfo(HttpServletRequest request , HttpServletResponse response){
        String id=request.getParameter("id");
        ActivityService service = new ActivityServiceImpl();
        Map map=service.getUpdateInfo(id);
        PrintJson.printJsonObj(response,map);
    }
    private void detail(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        ActivityService activityService = new ActivityServiceImpl();
        Activity activity = activityService.detail(id);
        request.setAttribute("a",activity);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);
    }
    private void getRemark(HttpServletRequest request,HttpServletResponse response){
        String id=request.getParameter("id");
        ActivityService activityService = new ActivityServiceImpl();
        List<ActivityRemark> list=activityService.getRemark(id);
        PrintJson.printJsonObj(response,list);
    }
    private void deleteRemark(HttpServletRequest request,HttpServletResponse response){
        String id=request.getParameter("id");
        ActivityService activityService = new ActivityServiceImpl();
        boolean flag=activityService.deleteRemark(id);
        PrintJson.printJsonFlag(response,flag);
    }
    private void saveRemark(HttpServletRequest request,HttpServletResponse response){
        String id = UUIDUtil.getUUID();
        String activityId = request.getParameter("activityId");
        String noteContent = request.getParameter("noteContent");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "0";
        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId(id);
        activityRemark.setActivityId(activityId);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setCreateBy(createBy);
        activityRemark.setCreateTime(createTime);
        activityRemark.setEditFlag(editFlag);

        ActivityService activityService = new ActivityServiceImpl();
        Boolean flag=activityService.saveRemark(activityRemark);
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("ar",activityRemark);
        PrintJson.printJsonObj(response,map);
    }

    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {
        String id=request.getParameter("id");
        String noteContent=request.getParameter("noteContent");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = request.getParameter("editBy");

        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId(id);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setEditTime(editTime);
        activityRemark.setEditBy(editBy);

        ActivityService activityService = new ActivityServiceImpl();
        boolean flag=activityService.updateRemark(activityRemark);
        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("ar",activityRemark);
        PrintJson.printJsonObj(response,map);
    }
}
