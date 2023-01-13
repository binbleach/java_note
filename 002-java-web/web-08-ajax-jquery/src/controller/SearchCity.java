package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.CityDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SearchCity extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       resp.setContentType("application/json;charset=utf-8");
        String json = "{}";
       String a= req.getParameter("cid");
        List list=CityDao.getCity(Integer.valueOf(a));
        ObjectMapper om = new ObjectMapper();
        json=om.writeValueAsString(list);
        PrintWriter pw = resp.getWriter();
        pw.print(json);
        pw.flush();
        pw.close();
    }
}
