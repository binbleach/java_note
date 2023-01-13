import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class _03sendRedirect extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
            1、浏览器请求路径：http://localhost:8080/myweb/1/sendRedirect
            2、服务器内部重定向发起路径：/myweb/Welcome
            3、浏览器地址栏路径：http://localhost:8080/myweb/Welcome
            2、服务器内部重定向发起路径：myweb/Welcome
            3、浏览器地址栏路径：http://localhost:8080/myweb/1/myweb/Welcome
            结论：
            1、重定向地址改变
            2、重定向 ”/“代表服务器根路径 http://localhost:8080/
            3、重定向不带 ”/“访问和请求转发不带 ”/“访问时一致，
                都是表示当前资源sendRedirect前面的相对路径http://localhost:8080/myweb/1/
        */

        //重定向，打在浏览器的地址栏上的所以肯定是get请求，重定向至少发送两次请求而只有其中一次是用户发的，状态码是302就是重定向发送的请求
        resp.sendRedirect("/myweb/Welcome");  //指向01Welcome
        //或者resp.sendRedirect("Welcome"); 没有斜杠
    }
}
