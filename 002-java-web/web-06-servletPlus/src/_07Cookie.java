import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
* Cookie类:
        1.介绍：
                  1) 来自于Servlet规范中提供一个工具类
                  2) 如果两个Servlet为同一个用户/用一个浏览器提供服务，
                      此时借助于用户的cookie实现数据共享
                  3) cookie在现实生活中相当于用户的会员卡
        4.生命周期
                 1）在正常情况下，cookie存放在浏览器内存中。
                       在浏览器关闭时，cookie将被销毁
                 2）在命令控制下，可以要求浏览器将接收cookie存放在客户端计算机
                       的硬盘上并指定存活时间。在存活时间范围内，关闭浏览器，关闭
                       服务器，关闭计算机都不会导致cookie销毁
                       cookie对象.setMaxAge(60); 在硬盘上存活1分钟
* */
@WebServlet(name = "_07Cookie")
public class _07Cookie extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie cookie = new Cookie("key","我是cookie值");
        response.addCookie(cookie);
    }
}
