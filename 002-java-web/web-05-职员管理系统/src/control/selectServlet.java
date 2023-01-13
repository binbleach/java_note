package control;
import entity.Dept;
import util.JdbcUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "selectServlet")
public class selectServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PreparedStatement pre=null;
        ResultSet pr=null;
        JdbcUtil car = new JdbcUtil();
        List <Dept> list = new ArrayList<>();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();

        String sql = "select * from dept where ?";
        pre = car.getcar(sql);

        try {
            pre.setInt(1,1);
            pr = pre.executeQuery();
            while (pr.next()){
                int deptno=pr.getInt(1);
                String dename = pr.getString(2);
                String loc = pr.getString(3);
                Dept dept = new Dept(deptno,dename,loc);
                list.add(dept);
            }
            out.print("<script type='text/javascript' src='/myweb/common.js'></script>");
            out.print("<table border='1px' align= 'center'>");
            out.print("<tr><td><input type='checkbox' onclick='fun1()'>全选与全不选</td>");
            out.print("<td>宿舍编号</td>");
            out.print("<td>宿舍姓名</td>");
            out.print("<td>宿舍地点</td>");
            out.print("<td colspan='2' align='center'>操作</td></tr>");
            for(Dept dept:list){
                out.print("<tr><td><input type='checkbox'></td>");
                out.print("<td>"+dept.deptno+"</td>");
                out.print("<td>"+dept.dename+"</td>");
                out.print("<td>"+dept.loc+"</td>");
                out.print("<td>删除宿舍</td>");
                out.print("<td>查询宿舍</td></tr>");
            }
            out.print("</table>");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
