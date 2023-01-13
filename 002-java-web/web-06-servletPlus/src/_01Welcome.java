import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class _01Welcome extends HttpServlet {
/*默认欢迎资源文件设置
         1.  默认欢迎资源文件：tomcat针对用户对当前网站发起默认请求，自动返回资源文件
         2.  默认请求：
           1）正常请求：
             http://localhost:8080/myWeb/one.html
           2) 默认请求：
            http://localhost:8080/myWeb/
       3.tomcat遵守的默认欢迎资源文件定位规则：
              1）规则位置：tomcat安装目录/conf/web.xml
     4.自定义网站中的默认欢迎资源文件定位规则：
        web.xml
            <welcome-file-list>
                    <welcome-file>这里直接写网站名如_01Welcome.html</welcome-file>
             </welcome-file-list>
        不写的话找index.html 和index.htm 和index.jsp
 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().print("我是重定向");  //_03sendRedirect重定向代码
    }
}
