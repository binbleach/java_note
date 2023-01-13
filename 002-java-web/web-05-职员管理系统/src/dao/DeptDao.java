package dao;

import util.JdbcUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeptDao {
    public int insert(String dename,String loc,HttpServletRequest request){
        int flag=0;
        String sql = "insert into dept(dename,loc) values(?,?)";
        JdbcUtil car =new JdbcUtil();
        //PreparedStatement pre=car.getcar(sql);
        PreparedStatement pre=car.getcar(sql, request);

        try {
            pre.setString(1,dename);
            pre.setString(2,loc);
            flag=pre.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            car.close(request);
        }
        return flag;
    }
}
