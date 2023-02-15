package com.huangjiabin.site.sys.controller;
import com.google.common.collect.Lists;


import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.model.User;
import com.huangjiabin.site.sys.model.UserRole;
import com.huangjiabin.site.sys.service.UserRoleService;
import com.huangjiabin.site.sys.service.UserService;
import com.huangjiabin.site.sys.util.ExcelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author huangjiabin
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/sys/files")
public class FilesController {
}

