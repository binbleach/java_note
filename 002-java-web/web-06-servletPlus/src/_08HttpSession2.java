import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "_08HttpSession2")
public class _08HttpSession2 extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        * 1) request.getSession():
                  如果当前用户在服务端拥有私人储物柜,要求tomcat返回
                 这个储物柜
                  如果当前用户在服务端尚未拥有私人储物柜，要求tomcat
                 为当前用户创建一个全新的私人储物柜
              2）request.getSession(false)
                 如果当前用户在服务端拥有私人储物柜,要求tomcat返回
                 这个储物柜
                 如果当前用户在服务端尚未拥有私人储物柜，要求tomcat返回
                 null
               3) 如果当前用户身份【经过登录验证审核判定为合法】，此时
                  应该使用request.getSession()来索要私人储物柜

            4）如果当前用户身份尚未确认合法，此时
                  应该使用request.getSession(false)来索要私人储物柜
        * */
        HttpSession session = request.getSession(false);
        String a=(String) session.getAttribute("黄家宾");
        System.out.println(a); //得先调用Session让他生成一个私人储物柜才能输出长得帅否则是null
    }
}
