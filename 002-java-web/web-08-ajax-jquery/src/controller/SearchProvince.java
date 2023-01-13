package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ProvinceDao;
import entity.Province;
import util.JDBCUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SearchProvince extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json="{}";
        String id=req.getParameter("proid2");
        Province p=ProvinceDao.searchProcince(Integer.valueOf(id));
        ObjectMapper m = new ObjectMapper();
        json=m.writeValueAsString(p);
        System.out.println(json);
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(json);
    }
}
