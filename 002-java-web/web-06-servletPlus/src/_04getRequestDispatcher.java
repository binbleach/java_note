import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
* 特征:
       1)请求转发过程中，浏览器发送了多少次请求
          只发送了一次请求
       2)请求转发过程中，为什么将请求对象和响应对象交给tomcat
           请求转发过程，两个Servlet应该共享同一个请求协议包，因此
           共享这个请求协议包关联的请求对象和响应对象
       3）通过请求转发调用的Servlet接收的请求方式
            在同一次请求转发过程中，所有servlet接收请求方式保持一致
* */
public class _04getRequestDispatcher extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
            1、浏览器请求路径：http://localhost:8080/myweb/1/RequestDispatcher
            2、服务器内部请求转发起路径：/Welcome
            3、浏览器获得资源路径：http://localhost:8080/myweb/Welcome
            2、服务器内部请求转发起路径：Welcome
            3、浏览器获得资源路径：http://localhost:8080/myweb/1/Welcome
            4、浏览器地址栏路径：http://localhost:8080/myweb/1/RequestDispatcher

            结论：
            1、请求转发地址不变
            2、请求转发带 ”/“代表应用根路径 有上下文的概念 http://localhost:8080/myweb/
            3、请求转发不带 ”/“访问和重定向不带 ”/“访问时一致，
                都是表示当前资源RequestDispatcher前面的相对路径http://localhost:8080/myweb/1/
        */
        RequestDispatcher re=req.getRequestDispatcher("/Welcome");//这里倒是有没有/都可以
        re.forward(req,resp);
    }
}
