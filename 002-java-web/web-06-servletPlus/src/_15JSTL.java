import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class _15JSTL extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        _16stu xu=new _16stu("20","徐广宸");
        _16stu cai = new _16stu("21","蔡徐坤");
        List list = new ArrayList();
        list.add(xu);
        list.add(cai);
        HttpSession session = req.getSession();
        req.setAttribute("stu",list);
        req.getRequestDispatcher("ELJSTL.jsp").forward(req,resp);

    }
}
