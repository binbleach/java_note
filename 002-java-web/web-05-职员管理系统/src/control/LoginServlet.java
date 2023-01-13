package control;

import dao.EmpDao;
import util.JdbcUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //创建dao对象
        EmpDao emp = new EmpDao();
        //取请求对象的参数
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String ck = request.getParameter("ck");
        //根据请求对象的参数判断密码是否正确
        int re=emp.login(username,password);

        //获取Cookie对象
        Cookie cookies[]=request.getCookies();
        //判断Cookie对象是否为空
        if(cookies!=null){
            //非空且符合本网站的令牌则放行
            for(Cookie card:cookies){
                if(card.getName().equals("staff")){
                    request.getSession();
                    request.getRequestDispatcher("index.html").forward(request,response);
                    return;
                }
            }
            //有身份牌cookie但是不是本网站的，拒绝并返回登录界面
            //request.getRequestDispatcher("login.html").forward(request,response);
        }

        //第一次来既没有密码也没有 Cookie身份牌，返回登录界面
        if(cookies==null && username==null &&password==null){
            request.getRequestDispatcher("login.html").forward(request,response);
            return;
        }

        //虽然没有Cookie身份令牌但是，提供了账号密码，而且账号密码正确，放行
        if(re==1){
            if(ck!=null){
                Cookie card = new Cookie("staff","emp");
                card.setMaxAge(60*60*24*10);
                response.addCookie(card);
            }
            request.getSession();
            request.getRequestDispatcher("index.html").forward(request,response);
        }else {
            //提供的账号密码错误，返回账号或密码错误提示的额登录界面
            request.getRequestDispatcher("errorlogin.html").forward(request,response);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //默认欢迎界面的请求方式是get的，所以这里我们调用一下post方法
        doPost(req,resp);
    }
}
