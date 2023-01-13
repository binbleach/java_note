import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//6.ServletContextListener接口：
//               监听全局作用域对象生命周期变化时刻
//
//      7.全局作用域对象生命周期：
//
//               1）在Tomcat启动时，自动为当前网站创建全局作用域对象
//               2）在Tomcat运行时，一个网站有且只能有一个全局作用域对象
//               3）在Tomcat关闭时，自动销毁网站中全局作用域对象
//
public class _11ServletContextListener implements ServletContextListener {
    @Override
    //全局作用域对象初始化的时候调用
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("恭喜你出生，今生今世平平安安");
    }

    //全局作用域对象将要被销毁的时候调用
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("兄弟你走好，媳妇我帮你照顾了");
    }
}
