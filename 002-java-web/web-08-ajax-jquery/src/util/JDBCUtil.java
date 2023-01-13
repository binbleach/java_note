package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUtil {
    Connection con = null;
    PreparedStatement car = null;
    public Connection getcon (){
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ajax","root","root");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
    public PreparedStatement getcar(String sql){
        try {
            car=getcon().prepareStatement(sql); //你得调用getcon拿车呀傻逼
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }
    public void closeCon(){
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void closeCar(){
        if(car!=null){
            try {
                car.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
