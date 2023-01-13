package control;

import entity.Emp;
import util.ReflectUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class addempServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //这里通过反射机制获取对象,取请求参数的参
        resp.setContentType("text/html;charset=utf-8");
        Object emp = ReflectUtil.init(Emp.class,req);
        PrintWriter out=resp.getWriter();
        int a=ReflectUtil.insert(emp,req);
        if(a==1){
            out.print("注册成功");
        }else {
            out.print("注册失败");
        }
        //
    }
}
