package util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.Iterator;
import java.util.Map;

public class JdbcUtil {
    Connection conn=null;
    PreparedStatement car = null;

    //普通的拿通道
    public Connection getcon(){
        //jdbc的Connection创建和销毁上最耗时间
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/staff_management_system","root","root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    //数据连接池拿通道
    public Connection getcon(HttpServletRequest request){
        ServletContext application=request.getServletContext();
        Map map=(Map)application.getAttribute("conPool");
        Iterator it=map.keySet().iterator();
        while (it.hasNext()){
            conn=(Connection) it.next();
            Boolean flag = (Boolean) map.get(conn);
            //System.out.println(flag);
            if(flag){
                map.put(conn,false);
                return conn;
            }
        }
        return null;
    }

    //数据连接池拿车
    public PreparedStatement getcar(String sql,HttpServletRequest request){
        conn=getcon(request);
        try {
            car=conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    //普通的拿车
    public PreparedStatement getcar(String sql){
        try {
            car=getcon().prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    public void close(HttpServletRequest request){
        ServletContext application=null;
        if(car!=null){
            try {
                car.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        application=request.getServletContext();
        Map map=(Map) application.getAttribute("conPool");
        map.put(conn,true);

    }

    public void close(){
        if(conn !=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(car!=null){
            try {
                car.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
