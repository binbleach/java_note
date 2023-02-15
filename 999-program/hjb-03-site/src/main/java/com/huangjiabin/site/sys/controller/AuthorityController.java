package com.huangjiabin.site.sys.controller;


import com.huangjiabin.site.sys.model.Authority;
import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.service.AuthorityService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-03
 */
@RestController
@RequestMapping("/sys/authority")
public class AuthorityController {
    @Autowired
    AuthorityService authorityService;

    @ApiOperation("根据用户id获取权限")
    @GetMapping("/getAuthorityById")
    public RespBean getAuthorityById(){
        List<Authority> authorityList = authorityService.getAuthorityByUserId();
        return RespBean.success("获取成功",authorityList);
    }

    @ApiOperation("获取所有权限和角色")
    @GetMapping("/getAuthorityWithRole")
    public RespBean getAllAuthority(){
        List<Authority> authorityList = authorityService.getAuthorityWithRole();
        return RespBean.success("获取成功",authorityList);
    }
}

