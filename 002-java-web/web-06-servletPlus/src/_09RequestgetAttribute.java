import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "_09RequestgetAttribute")
public class _09RequestgetAttribute extends HttpServlet {
    /*前提条件: 在一次请求转发过程中，两个Servlet共享同一个请求协议包。
                          因此两个Servlet共享这个请求协议包中请求对象和响应对象
                          所以在请求转发过程中两个Servlet可以通过当前请求对象实现
                         数据共享。
                         当请求对象实现数据共享时使用内部attribute（ConcurrentHashMap）属性存储共享数据
                         此时开发人员将请求对象称为【请求作用域对象】*/
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String a=(String) request.getAttribute("黄家宾");
        System.out.println(a);
    }
}
