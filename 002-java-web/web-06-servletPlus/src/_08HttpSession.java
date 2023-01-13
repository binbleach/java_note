import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/*
会话作用域对象
1）在浏览器关闭时，服务端是否会销毁用户的session[面试]
                  在浏览器关闭时，切断了用户与浏览器之间联系
                  但是tomcat是服务端计算机中资源调度软件无法侦测浏览器
                  何时关闭的，因此在浏览器关闭时，服务端是不会销毁用户的session
             2）tomcat在创建session对象时，设置一个【最大空闲时间】。
                  如果session对象从上次使用完毕到现在空闲时间达到了【最大空闲时间】
                  tomcat认为用户放弃了自己的session.此时tomcat负责销毁
            3）tomcat设置【最大空闲时间】是30分钟
            4）网站自行设置session 【最大空闲时间】

                  web.xml
                  <session-config>
                          <session-timeout>5</session-timeout><!--session最大空闲时间5分钟-->
                 </session-config>
* */
@WebServlet(name = "_08HttpSession")
public class _08HttpSession extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("黄家宾","长得帅");
    }
}
