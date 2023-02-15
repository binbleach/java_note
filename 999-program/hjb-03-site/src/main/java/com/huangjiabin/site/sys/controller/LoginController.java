package com.huangjiabin.site.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.huangjiabin.site.sys.model.DictList;
import com.huangjiabin.site.sys.model.RespBean;
import com.huangjiabin.site.sys.model.Student;
import com.huangjiabin.site.sys.model.User;
import com.huangjiabin.site.sys.service.*;
import com.huangjiabin.site.sys.util.IpUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sys")
public class LoginController {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private DictListService dictListService;

    @ApiOperation(value = "登录之返回token")
    @PostMapping("/login/{username}/{password}/{code}")
    public RespBean login(@PathVariable("username") String username,
                               @PathVariable ("code") String code,@PathVariable("password") String password, HttpServletRequest request){
        return loginService.login(username,password,code,request);
    }


    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/getLoginInfo")
    public RespBean getLoginInfo(HttpServletRequest request,Principal principal){
        if(null==principal){
            return null;
        }
        String username = principal.getName();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",username);
        User user= userService.getOne(queryWrapper);
        user.setRoles(roleService.selectRoleByUserId(user.getId()));
        user.setPassword(null);
        QueryWrapper queryWrapper2 = new QueryWrapper();
        queryWrapper2.eq("student_id",user.getId());
        Student student = studentService.getOne(queryWrapper2);
        Map<String,Object> map = new HashMap<>();
        map.put("user",user);
        map.put("student",student);

        String ip= IpUtil.getIpAddr(request);
        String info = IpUtil.getIpInfo(ip);
        map.put("address",info);
        return RespBean.success("获取成功",map);
    }
    @ApiOperation(value = "test")
    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logOut(){
        return RespBean.success("退出成功");
    }

    @Async
    @ApiOperation("获取验证码")
    @GetMapping(value = "/captcha",produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        response.setDateHeader("Expires",0);
        response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
        response.addHeader("Cache-Control","post-check=0,pre-check=0");
        response.setHeader("Access-Control-Allow-Credentials", "true");//允许携带cookie
        response.setHeader("Pragma","no-cache");
        response.setContentType("image/jpeg");
        //-------------------生成验证begin------------------
        //获取验证码文本内容
        String text = defaultKaptcha.createText();
        System.out.println("生成的验证码========"+text);
//        request.getSession().setAttribute("captcha",text);
//        ValueOperations<String,Object> valueOperations=redisTemplate.opsForValue();
//        valueOperations.set(email,text);
        //根据文本验证码内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(text);
        ServletOutputStream outputStream = null;
        try {
            synchronized(ImageIO.class){         /*传进去的很重要必须是两个线程共享的对象*/
            outputStream = response.getOutputStream();
            ImageIO.write(image,"jpg",outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null!=outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //-------------------生成验证end------------------
    }
    @ApiOperation(value = "获取字典数据")
    @GetMapping("/getDict")
    public RespBean getDict(){
        List<DictList> allDictListWithDate = dictListService.getAllDictListWithDate();
        return RespBean.success("字典加载成功",allDictListWithDate);
    }


}
