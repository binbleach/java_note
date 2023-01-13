import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class _06ServletContext extends HttpServlet {
    @Override
    /*
    * servlet之间数据共享方案 ServletContext 全局作用域对象
             1.介绍:
                 1)ServletContext接口来自于Servlet规范
                 2)ServletContext接口实现类由Http服务器厂商提供
                 3)ServletContext接口修饰对象被称为全局作用域对象
                 4)来自于同一个网站下Servlet都可以使用全局作用域对象进行数据共享
                 *
       1）如果不进行限制，无限向全局作用域对象添加数据。导致
                           服务端计算机内存消耗殆尽
                      2）容易导致数据污染
    *
    * */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //全局作用域对象
        ServletContext application=req.getServletContext();  //作用于_06ServletContext2
        application.setAttribute("ap",100);
    }
}
