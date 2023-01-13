package com.huangjiabin.mimissm.service.impl;

import com.huangjiabin.mimissm.mapper.AdminMapper;
import com.huangjiabin.mimissm.pojo.Admin;
import com.huangjiabin.mimissm.pojo.AdminExample;
import com.huangjiabin.mimissm.service.AdminService;
import com.huangjiabin.mimissm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        //添加条件查询条件
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andANameEqualTo(name);
        //根据条件查询admin
        List<Admin> list= adminMapper.selectByExample(adminExample);
        if(list.size()>0){
            Admin admin = list.get(0);
            if(MD5Util.getMD5(pwd).equals(admin.getaPass())){
                return admin;
            }
        }
        return null;
    }
}
