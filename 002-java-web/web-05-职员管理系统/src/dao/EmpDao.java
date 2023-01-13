package dao;

import util.JdbcUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmpDao {

    public int login(String usernaem,String password) {
        String sql = "select count(*) from emp where username=? and password=?";
        PreparedStatement car = (PreparedStatement) new JdbcUtil().getcar(sql);
        ResultSet re = null;
        int flag =0;
        try {
            car.setString(1, usernaem);
            car.setString(2, password);
            re = car.executeQuery();
            re.next();
            flag = re.getInt(1);  //这是一种写法因为通过计数的方式获取ResultSet,所以第一行是一。
            System.out.println("-------------------");
            System.out.println(flag);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
