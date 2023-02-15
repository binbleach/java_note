package com.huangjiabin.site.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huangjiabin.site.sys.model.*;
import com.huangjiabin.site.sys.service.RoleService;
import com.huangjiabin.site.sys.service.StudentService;
import com.huangjiabin.site.sys.service.UserRoleService;
import com.huangjiabin.site.sys.service.UserService;
import com.huangjiabin.site.sys.util.EntityUtil;
import com.huangjiabin.site.sys.util.ExcelUtils;
import com.huangjiabin.site.sys.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiOperation;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangjiabin
 * @since 2022-02-12
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private StringEncryptor stringEncryptor;

    @ApiOperation(value = "分页查询用户基本信息")
    @GetMapping("/getUserPage/{current}/{size}")
    public RespBean getUserList(@PathVariable("size") Long size,@PathVariable("current") Long current) {
        IPage<User> page  = new Page();
        //设置分页的数据
        page.setCurrent(current);//页码
        page.setSize(size);// 每页的记录数
        IPage<User> result = userService.page(page);
        return RespBean.success("true",result);
    }

    @ApiOperation(value = "创建用户账号，并返回用户信息")
    @PostMapping("/createUser")
    public RespBean createUser(@RequestBody Map map, HttpServletRequest request){
        //请求头中获取Authorization值
        String autHeader=request.getHeader("Authorization");
        Claims claims = null;
        try {
            //存在token，先判断是否存在Authorization值，值是否以Bearer开头
//            if(null!=autHeader && autHeader.startsWith("Bearer")) {
//                //从Authorization值中获取token
//                String emailToken = autHeader.substring("Bearer".length());
//                if(jwtTokenUtil.isTokenExpiration(emailToken)){
//                    return RespBean.error("验证码失效请重新获取！！！");
//                }else {
//                    if(!jwtTokenUtil.getCodeByToken(emailToken).equals(map.get("code"))){
//                        return RespBean.error("验证码错误");
//                    }
//                }
//            }else {
//                RespBean.error("无效token");
//            }
            User user = EntityUtil.mapToBean(map, User.class);
            RespBean respBean=userService.checkUserCanCreate(user);
            if(respBean.getCode()!=200){
                return respBean;
            }
            user.setPassword(stringEncryptor.encrypt(user.getPassword()));
            user.setCredit(6);
            user.setDelFlag(0);
            user.setDisabled(0);
            userService.save(user);
            UserRole userRole = new UserRole(user.getId(),new Long(user.getTitle()));
            userRoleService.save(userRole);
            RespBean success = RespBean.success("账号创建成功", user);
            return success;
        } catch (ExpiredJwtException e) {
                claims = e.getClaims();
            return RespBean.error("验证码过期",claims);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("用户创建失败");
    }
    @ApiOperation(value = "创建用户账号，无需邮箱验证")
    @PostMapping("/createUserT")
    public RespBean createUserT(@RequestBody Map map, HttpServletRequest request){
        try {

            String captcha = (String) request.getSession().getAttribute("captcha");
            System.out.println(captcha);
            User user = EntityUtil.mapToBean(map, User.class);
            RespBean respBean=userService.checkUserCanCreate(user);
            if(respBean.getCode()!=200){
                return respBean;
            }
            user.setCredit(6);
            user.setDelFlag(0);
            user.setDisabled(0);
            userService.save(user);
            UserRole userRole = new UserRole(user.getId(),new Long(user.getTitle()));
            userRoleService.save(userRole);
            RespBean success = RespBean.success("账号创建成功", user);
            return success;
        } catch (ExpiredJwtException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("用户创建失败");
    }

    @ApiOperation(value = "创学生账号，成功返回学生信息")
    @Transactional
    @PostMapping("/createUserStudent")
    public RespBean createUserStudent(@RequestBody(required = false) Map<String,Object> map, HttpServletRequest request){
        try {
        //请求头中获取Authorization值
        String autHeader=request.getHeader("Authorization");
        String captcha = (String) request.getSession().getAttribute("captcha");
        System.out.println("获取到的验证码"+captcha);
        Claims claims = null;
        //存在token，先判断是否存在Authorization值，值是否以Bearer开头
        if(null!=autHeader && autHeader.startsWith("Bearer")) {
            //从Authorization值中获取token
            String emailToken = autHeader.substring("Bearer".length());
            if(jwtTokenUtil.isTokenExpiration(emailToken)){
                return RespBean.error("验证码失效请重新获取！！！");
            }else {
                if(!jwtTokenUtil.getCodeByToken(emailToken).equals(map.get("code"))){
                    return RespBean.error("验证码错误");
                }
            }
        }else {
            return RespBean.error("无效token");
        }
        boolean result1;
        boolean result2 = false;
        Student student = new Student();
            User user = EntityUtil.mapToBean(map, User.class);
            RespBean respBean=userService.checkUserCanCreate(user);
            if(respBean.getCode()!=200){
                return respBean;
            }
            user.setCredit(6);
            user.setDelFlag(0);
            user.setDisabled(0);
            result1 = userService.save(user);
            UserRole userRole = new UserRole(user.getId(),new Long(28));
            userRoleService.save(userRole);
            if(result1){
                student = EntityUtil.mapToBean(map, Student.class);
                student.setStudentId(user.getId());
                result2 = studentService.save(student);
            }
            if(result2){
                Map<String,Object> map1 = new HashMap();
                map1.put("user",user);
                map1.put("student",student);
                RespBean success = RespBean.success(result1+"", map1);
                return success;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        RespBean error = RespBean.error("创建失败");
        return error;
    }

    @ApiOperation(value = "更改用户信息，传所有参数")
    @PutMapping("/updateUser")
    public RespBean updateUser(@RequestBody User user){
        boolean result = userService.updateById(user);
        if(result){

            return RespBean.success("更改成功",user);
        }
        return RespBean.error("更改失败");
    }
    @ApiOperation(value = "根据用户id删除用户信息，物理删除不可恢复")
    @DeleteMapping("/deleteUserById/{id}")
    public RespBean deleteUserById(@PathVariable("id") Long id){
        boolean result = userService.removeById(id);
        if(result){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
    @ApiOperation(value = "根据用户id获取用户信息，未被删除")
    @GetMapping("/getUserByIdP/{id}")
    public RespBean getUserById(@PathVariable("id") Long id){
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("del_flag",0);
        qw.eq("id",id);
        User user = userService.getOne(qw);
        if(user!=null){
            return RespBean.success("查询成功",user);
        }
        return RespBean.error("查询失败");
    }
    @ApiOperation(value = "根据用户id删除用户信息，逻辑删除可恢复")
    @DeleteMapping("/deleteUserByIdLogic/{id}")
    public RespBean deleteUserByIdLogic(@PathVariable("id") Long id){
        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.set("del_flag",1);
        uw.eq("id",id);
        boolean success = userService.update(uw);
        if(success){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
    @ApiOperation(value = "根据用户id恢复删除用户")
    @PutMapping("/recoveryUserById/{id}")
    public RespBean recoveryUserById(@PathVariable("id") Long id){
        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.set("del_flag",0);
        uw.eq("id",id);
        boolean success = userService.update(uw);
        if(success){
            return RespBean.success("恢复成功");
        }
        return RespBean.error("恢复失败");
    }

    @ApiOperation(value = "根据用户id禁用用户")
    @PutMapping("/updateUserDisabledById/{id}")
    public RespBean updateUserDisableById(@PathVariable("id") Long id){
        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.set("disabled",1);
        uw.eq("id",id);
        boolean success = userService.update(uw);
        if(success){
            return RespBean.success("禁用成功");
        }
        return RespBean.error("禁用失败");
    }
    @ApiOperation(value = "根据用户id激活用户")
    @PutMapping("/updateUserEnabledById/{id}")
    public RespBean updateUserEnabledById(@PathVariable("id") Long id){
        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.set("disabled",0);
        uw.eq("id",id);
        boolean success = userService.update(uw);
        if(success){
            return RespBean.success("激活成功");
        }
        return RespBean.error("激活失败");
    }
    @ApiOperation(value = "批量创建用户，不可创建管理员")
    @PostMapping("/createUserBatch")
    public RespBean createUserBatch(@RequestParam("file") MultipartFile file) {
        Map<String,Object> map = new HashMap<>(16);
        //调用工具类解析excel文件
        List<ArrayList<String>> row = ExcelUtils.analysis(file);
        List<User> userList = new ArrayList<>();
        //打印信息
        for (int i = 0;i<row.size();i++){
            User user = new User();
            user.setUsername(row.get(i).get(0));
            user.setPassword(row.get(i).get(1));
            user.setAddress(row.get(i).get(2));
            //user.setGender(Integer.valueOf(row.get(i).get(3)));
            if(row.get(i).get(3)=="男"){
                user.setGender(16);
            }else {
                user.setGender(17);
            }
            user.setNickname(row.get(i).get(4));
            user.setPhone(row.get(i).get(5));
            if(row.get(i).get(6)=="学生"){
                user.setTitle(28);
            }else {
                user.setTitle(29);
            }
            user.setEmail(row.get(i).get(7));
            user.setDisabled(0);
            user.setCredit(6);
            user.setDelFlag(0);
            user.setPhoto("ce6ceddd1d9a46f6bdc1a09af76ca7b7-小埋.jpg");
            //旧密码不设置
            //user.setOldPassword("");
            //角色不设置
            //user.setRoles(Lists.newArrayList());
            userList.add(user);
        }
        try {
            boolean b = userService.saveBatch(userList);
            if (b) {
                ArrayList<UserRole> userRoleList = new ArrayList<>();
                for (User user : userList) {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(user.getId());
                    if (user.getTitle() == 28) {
                        userRole.setRoleId(new Long(28));
                    } else {
                        userRole.setRoleId(new Long(29));
                    }
                    userRoleList.add(userRole);
                }
                userRoleService.saveBatch(userRoleList);
            }
            return RespBean.success("创建成功");
        }catch (Exception e){
            return RespBean.error("创建失败，请检查信息是否正确",e.getMessage());
        }
    }

    @GetMapping(value = "/downModel")
    public void download(HttpServletRequest request,HttpServletResponse response) {

        String filename = "批量注册模板.xls";

        // 设置信息给客户端不解析
        String type = new MimetypesFileTypeMap().getContentType(filename);
        // 设置contentType，即告诉客户端所发送的数据属于什么类型
        response.setHeader("Content-type",type);
        try {
            // 设置编码
            String hehe = new String(filename.getBytes("utf-8"), "iso-8859-1");
            // 设置扩展头，当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型。
            response.setHeader("Content-Disposition", "attachment;filename=" + hehe);
            ExcelUtils.download(filename,request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @ApiOperation(value = "查询用户角色")
    @GetMapping(value = "/getUserRolesById/{id}")
    public RespBean getUserRolesById(@PathVariable("id")Long id) {
        List<Role> roles = roleService.selectRoleByUserId(id);
        return RespBean.success("查询成功",roles);
    }
    @ApiOperation(value = "删除用户角色")
    @DeleteMapping(value = "/deleteUserRole/{userId}/{roleId}")
    public RespBean deleteUserRole(@PathVariable("userId")Long userId,@PathVariable("roleId")Long roleId) {
        QueryWrapper<UserRole> userRoleQueryWrapper = new QueryWrapper<>();
        userRoleQueryWrapper.eq("user_id",userId);
        userRoleQueryWrapper.eq("role_id",roleId);
        try {
            boolean remove = userRoleService.remove(userRoleQueryWrapper);
            if (remove) {
                return RespBean.success("删除成功");
            } else {
                return RespBean.error("删除失败");
            }
        }catch (DataIntegrityViolationException e){
            return RespBean.error("删除失败，请检查用户或角色是否存在");
        }
    }
    @ApiOperation(value = "添加用户角色")
    @PostMapping(value = "/addUserRole/{userId}/{roleId}")
    public RespBean addUserRole(@PathVariable("userId")Long userId,@PathVariable("roleId")Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        try {
            boolean save = userRoleService.save(userRole);

            if (save) {
                return RespBean.success("添加成功");
            } else {
                return RespBean.error("添加失败");
            }
        }catch (DuplicateKeyException e){
            return RespBean.error("添加失败，重复角色");
        }catch (DataIntegrityViolationException e){
            return RespBean.error("添加失败，请检查用户或角色是否存在");
        }
    }

}

