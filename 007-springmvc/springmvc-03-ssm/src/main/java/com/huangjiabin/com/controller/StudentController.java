package com.huangjiabin.com.controller;

import com.huangjiabin.com.entity.Student;
import com.huangjiabin.com.exception.AgeException;
import com.huangjiabin.com.exception.MyUserException;
import com.huangjiabin.com.exception.NameException;
import com.huangjiabin.com.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /*业务模块*/
    @RequestMapping(value = "/addStudent.do")
    public ModelAndView addStudent(Student student){
        ModelAndView mv = new ModelAndView();
        int rows=studentService.addStudent(student);
        if (rows>0){
            mv.addObject("msg","注册成功");
        }else {
            mv.addObject("msg","注册失败");
        }
        mv.setViewName("result");
        return mv;
    }

    @RequestMapping(value = "/queryStudents.do")
    @ResponseBody
    public List<Student> queryStudents(){
        List<Student> list = studentService.selectStudent();
        return list;
    }

    /*请求转发
    * 无视视图解析器，不能和视图解析器一起用。有时候有的在view包下，有的不在view包下的视图也可以访问了。
    * */
    @RequestMapping(value = "/requestDispatcher.do")
    public ModelAndView requestDispatcher(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","这里是请求转发，特点是不和视图解析器共同工作");
        mv.addObject("msg2","无视视图解析器");
        mv.setViewName("forward:/request.jsp");
        return mv;
    }

    /*重定向
    * 重定向不能访问/WEB-INF/包下的文件。因为不能通过浏览器地址栏访问/WEB-INF/下的文件。咱也是通过这样防止跨权限访问的
    * */
    @RequestMapping(value = "/sendRedirect.do")
    public ModelAndView sendRedirect(){
        ModelAndView mv = new ModelAndView();
        mv.addObject("myName","徐凤年");
        mv.addObject("myAge",23);
        mv.setViewName("redirect:/redirect.jsp");
        return mv;
    }

    /*用于sprigMVC异常处理
    * 1、定义异常类。
    * 2、定义处理异常的 handler.GlobalExceptionHandler类,处理异常类的方法doNameException。
    * 3、创建扫描器扫描controller,handler,exception。
    * 4、创建<mvc:annotation-driven/> 注解驱动。
    * */
    @RequestMapping(value = "/exception.do")        //抛父类就可以了
    public ModelAndView nameException(Student s) throws MyUserException {
        ModelAndView mv = new ModelAndView();
        if(s.getName().equals("徐凤年")){
           throw new NameException("徐凤年不能来");
        }
        if(s.getAge()<18||s.getAge()>80){
            throw new AgeException("年龄不允许");
        }
        mv.setViewName("forward:queryStudents.do");
        return mv;
    }
}
