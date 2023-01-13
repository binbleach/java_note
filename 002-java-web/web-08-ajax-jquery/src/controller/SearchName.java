package controller;

import dao.ProvinceDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static dao.ProvinceDao.searchProcince;

public class SearchName extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("我被ajax调用了");
        String id= req.getParameter("proid");
        System.out.println(searchProcince(Integer.valueOf(id)));
        String name=ProvinceDao.searchName(Integer.valueOf(id));
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().print(name);
    }
}
