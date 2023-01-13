package control;

import dao.DeptDao;
import util.JdbcUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Date;

@WebServlet(name = "addServlet")
public class addServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreparedStatement car=null;
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String dename=request.getParameter("dename");
        String loc= request.getParameter("loc");
        DeptDao dept = new DeptDao();

        Date start = new Date();
        int flag=dept.insert(dename,loc,request);
        Date end = new Date();

        System.out.println("消耗时间是:"+(end.getTime()-start.getTime())+"ms");
        if(flag==1){
            out.print("插入成功");
        }else{
            out.print("插入失败");
        }


    }
}
