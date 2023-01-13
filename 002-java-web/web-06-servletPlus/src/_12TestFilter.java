import javax.servlet.*;
import java.io.IOException;

/*
    用于测试：声明拦截器时的名称影响不影响拦截器的调用，结果是不影响。
    过程：声明 <filter-name>_12TestFilter</filter-name>
    结果：调用的还是_12Filter
*/
public class _12TestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("没想到吧是我");
    }
}
