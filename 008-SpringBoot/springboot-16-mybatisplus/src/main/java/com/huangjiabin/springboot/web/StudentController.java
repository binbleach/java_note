package com.huangjiabin.springboot.web;

import com.huangjiabin.springboot.model.Student;
import com.huangjiabin.springboot.service.StudentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Api(tags = "校园公共活动场所管理系统api")
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @ApiOperation(value = "创建学生用户", notes = "传入学生信息创建学生用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "name", required = true, value = "姓名",  dataType = "String"),
            @ApiImplicitParam(paramType = "path", name = "sno",required = true, value = "学号", dataType = "String"),
            @ApiImplicitParam(paramType = "path", name = "phone",required = true, value = "电话", dataType = "String"),
            @ApiImplicitParam(paramType = "path", name = "dormitory",required = true, value = "宿舍", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "缺少必要的请求参数"),
            @ApiResponse(code = 401, message = "请求路径没有相应权限"),
            @ApiResponse(code = 403, message = "请求路径被隐藏不能访问"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 405, message = "请求方法不支持"),
    })
    @PostMapping(value="/student/create/{name}/{sno}/{phone}/{dormitory}")
    public Student createStudentUser (@PathVariable("name") String name,@PathVariable("sno") String sno,
    @PathVariable("phone") String phone,@PathVariable("dormitory") String dormitory){
        Student student = new Student();
        student.setName(name);
        student.setSno(sno);
        student.setPhone(phone);
        student.setDormitory(dormitory);
        int result=studentService.addStudentUser(student);
        if(result>0){
            return student;
        }else {
            return null;
        }
    }
    /*
        关于更新操作
        1、如果属性为空且为包装类型如Integer则该属性不更新
        2、如果属性为空且为基本类型如int则该属性为0。
        3、因为判断是否更新是看是否null，基本类型int默认类型为0。
        4、所有包装类型默认类型为null
    */
    @PutMapping(value="/student/create/{id}/{name}/{sno}/{phone}/{dormitory}")
    public Student updateStudentById (@PathVariable("id") String id,@PathVariable("name") String name,@PathVariable("sno") String sno,
                                      @PathVariable("phone") String phone,@PathVariable("dormitory") String dormitory){
        Student student = new Student();
        student.setId(id);
        student.setName(name);
        student.setSno(sno);
        student.setPhone(phone);
        student.setDormitory(dormitory);
        int result=studentService.updateStudentById(student);
        if(result>0){
            return student;
        }else {
            return null;
        }
    }

}
