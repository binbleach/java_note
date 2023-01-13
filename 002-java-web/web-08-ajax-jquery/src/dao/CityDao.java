package dao;

import entity.City;
import util.JDBCUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CityDao {
    public static List getCity(int id){
        List list = new ArrayList();
        JDBCUtil jdbc = new JDBCUtil();
        PreparedStatement car =null;
        ResultSet rs = null;
        String sql ="select name from city where provinceid = ?";
        car=jdbc.getcar(sql);
        try {
            car.setInt(1,id);
            rs=car.executeQuery();
            while (rs.next()){
                City city = new City();
                city.setName(rs.getString("name"));
                list.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
