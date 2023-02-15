package com.huangjiabin.site.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huangjiabin.site.sys.model.Authority;
import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.model.Role;
import com.huangjiabin.site.sys.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation("查询所有角色")
    @GetMapping("/getAllRole")
    public RespBean getAllRole(){
        List<Role> roleList = roleService.list();
        if(!CollectionUtils.isEmpty(roleList)) {
            return RespBean.success("查询成功", roleList);
        }else {
            return RespBean.error("查询失败");
        }
    }
    @ApiOperation("查询所有角色和权限")
    @GetMapping("/getAllRoleWithAuthority")
    public RespBean getAllRoleWithAuthority(){
        List<Role> roleList = roleService.selectRoleWithAuthority();
        if(!CollectionUtils.isEmpty(roleList)) {
            return RespBean.success("查询成功", roleList);
        }else {
            return RespBean.error("查询失败");
        }
    }
}

