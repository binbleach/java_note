import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ThreeServlet")
public class FourServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("post请求方式");
        request.setCharacterEncoding("utf-8");//设置了输入中文名也不会乱码了
        String pre=request.getParameter("postrq");  //不设置什么的话输入中文会乱码，因为请求体中的信息是request对象解析的
        System.out.println(pre);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("GET请求方式处理");
        RequestDispatcher five=request.getRequestDispatcher("two.html");//request对象向tomcat索要资源
        five.forward(request,response);
    }
}
