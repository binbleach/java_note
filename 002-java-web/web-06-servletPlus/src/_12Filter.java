import javax.servlet.*;
import java.io.IOException;
//过滤器
/*
* 1）Filter接口来自于Servlet规范
       2）Filter接口实现类由项目开发人员实现
       3)  Filter接口可以在tomcat调用资源文件之前进行拦截
* */
//01继承接口
public class _12Filter implements Filter {
    @Override
    //02重写 doFilter方法
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String age=servletRequest.getParameter("age");
        if(age!=null && Integer.valueOf(age)>3){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            servletResponse.setContentType("text/html;charset=utf-8");
            servletResponse.getWriter().print("你还小看不懂");
        }
        //除了这个还可以增强servlet像是给每个servlet加一个request.setCharacterEncoding("utf-8");
        //防止读取请求参数是乱码
    }
    /*03.过滤器拦截地址：

      1）通知tomcat调用某个具体资源文件时需要调用当前过滤器
           <url-pattern>/文件名.后缀名</url-pattern>

      2）通知tomcat调用某个文件夹下资源文件时需要调用当前过滤器
            <url-pattern>/文件夹名/*</url-pattern>

      3) 通知tomcat调用某种类型文件时需要调用当前过滤器
          <url-pattern>*.jpg</url-pattern>

      4) 通知tomcat在调用当前网站下任意资源文件时需要调用当前过滤器
         <url-pattern>/*</url-pattern>

    * */
}
