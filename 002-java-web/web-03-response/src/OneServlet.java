import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "OneServlet")
public class OneServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
/*
* 1）HttpServletResponse接口来自于Servlet规范
  2）HttpServletResponse接口实现类由Http服务器厂商提供
  3）HttpServletResponse接口可以将Servlet中运行结果写入到响应包
* */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "Hello world";

        /*HttpServletResponse接口负责设置响应包中响应头中content-type属性
        控制浏览器采用对应的解析器和编译器对响应体中二进制数据进行处理*/
        response.setContentType("text/html;charset=utf-8");   //这个得写在前面

        /*HttpServletResponse接口负责将Servlet运行结果以二进制形式
        写入到响应包中响应体*/
        PrintWriter w = response.getWriter();

        w.print(url);

        String url2 = "<br/>Java<br/>Mysql<br/>Jdbc";
        String url3="<br/>海南<br/>成都<br/>";

        w.print(url2);
        w.print(url3);

    }
}
