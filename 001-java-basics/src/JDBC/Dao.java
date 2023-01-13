package JDBC;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Dao {
    public int select(){
        Connection conn = null;
        PreparedStatement pre = null;
        ResultSet re = null;
        String sqls  = "select * from dept where ?";
        return 1;
    }
}
