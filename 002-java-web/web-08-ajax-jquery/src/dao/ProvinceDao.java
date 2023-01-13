package dao;

import entity.Province;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProvinceDao {

    public static String searchName(Integer id){
        PreparedStatement car = null;
        ResultSet re= null;
        String name = null;
        String sql="select name from province where id=?";
        JDBCUtil jdbc = new JDBCUtil();
        car=jdbc.getcar(sql);
        try {
            car.setInt(1,id);
            re=car.executeQuery();
            if(re.next()){
                name=re.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            jdbc.closeCon();
            jdbc.closeCar();
        }
        return name;
    }
    public static Province searchProcince(Integer id){
        Province province=null;
        PreparedStatement car = null;
        ResultSet re= null;
        String name = null;
        String jiancheng;
        String shenghui;
        String sql="select * from province where id=?";
        JDBCUtil jdbc = new JDBCUtil();
        car=jdbc.getcar(sql);
        try {
            car.setInt(1,id);
            re=car.executeQuery();
            if(re.next()){
                name=re.getString("name");
                jiancheng=re.getString("jiancheng");
                shenghui=re.getString("shenghui");
                province = new Province(id,name,jiancheng,shenghui);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            jdbc.closeCon();
            jdbc.closeCar();
        }
        return province;
    }

    public List<Province> getAllName(){
        List list = new ArrayList();
        PreparedStatement car = null;
        ResultSet rs = null;
        String sql = "select id,name from province order by id";
        JDBCUtil jdbc = new JDBCUtil();
        car=jdbc.getcar(sql);
        try {
            rs=car.executeQuery();
            while (rs.next()){
                Province pro = new Province();
                pro.setName(rs.getString("name"));
                pro.setId(rs.getInt("id"));
                list.add(pro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
