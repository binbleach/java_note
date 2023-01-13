package JDBC;

import java.io.DataOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class PreparedStatement {
    public static void main(String[] args) {
        System.out.println("欢迎来到登录系统！");
        System.out.println("账号:");
        Scanner s = new Scanner(System.in);
        String in = s.next();

        Connection conn = null;
        Statement stat = null;
        try {
            //DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());//注册驱动
            stat= conn.createStatement();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        System.out.println("密码:");

    }
}
