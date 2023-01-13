import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "OneServlet")
public class OneServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //功能1)读取请求包中请求行中信息【url,method】
        String uri=request.getRequestURI();//这里是uri
        String url=request.getRequestURL().toString();//这才是url
        String method = request.getMethod();

        System.out.println(url);
        System.out.println(uri);
        System.out.println(method);

    }
}
