package JDBC;

import java.sql.*;

/*
* 第一个jdbc程序
* */
public class JDBC {

    public static void main(String[] args){
        Connection conn = null;
        Statement stat =null;
        ResultSet re = null;
        try {
            //第一步注册驱动
            //DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());//用了最新的数据库要用cj
            //第二步获取连接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schema","root","root");
            //获取数据库操作对象
            stat = conn.createStatement();
            //执行sql
            String select = "select * from emp";
            //处理查询结果集
            re = stat.executeQuery(select);
            /*int ree =  stat.executeUpdate(select);
            System.out.println(ree);*/
            while(re.next()){
                System.out.println(re.getString("empno")+","+re.getString("ename"));
                System.out.println(re.getString("ename"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if (conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stat!=null){
                try {
                    stat.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
