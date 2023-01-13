import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(name = "TwoServlet")
public class TwoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //2) 读取请求包中请求头参数信息
        String name=request.getParameter("userName");
        String password = request.getParameter("password");
        System.out.println("name="+name);
        System.out.println("password="+password);

        //如果是null则是没有参数，当我们不知道有多少个参数时：
        Enumeration paramNames =request.getParameterNames();//返回的是一个枚举类型的参数
        while(paramNames.hasMoreElements()){
            String paraName=(String)paramNames.nextElement();//next执行时先下指针才看有没有，这个是先看有没有
            String value=request.getParameter(paraName);
            System.out.println("请求参数的名称："+paraName+"请求参数的值："+value);
        }
    }
}
