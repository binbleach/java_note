package control;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SafeFilter implements Filter {
    /*
    * 防止用户恶意登录
    * */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        String uri=request.getRequestURI();
        if(uri.indexOf("login")!=-1 || uri.equals("/myweb/")){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        HttpSession session=request.getSession(false);
        if(session!=null){
            //request.getRequestDispatcher("errorlogin.html");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }else {
            request.getRequestDispatcher("errorlogin.html");
        }
    }
}
