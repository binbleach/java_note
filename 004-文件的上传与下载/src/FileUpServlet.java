import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(name = "FileUpServlet")
public class FileUpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //手工接收上传文件，前端是以二进制流的形式分段传过来的，那我们接收就必须以流的形式接收
        InputStream in = request.getInputStream();
        byte bytes[] = new byte[10240000];
        int readLen = in.read(bytes);
        //输出的和图片一模一样
        System.out.println(new String(bytes,0,readLen));
        in.close();
    }
}
