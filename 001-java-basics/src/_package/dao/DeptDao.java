package _package.dao;
/*
* 数据存储对象，用来封装对数据的操作
* 通俗来讲，就是将数据库操作都封装起来。也就是基于流程的封装
* */

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeptDao {
    public int update(){
        String sqlupdate = "update dept set dname = ? loc = ? where deptno = ?";
        Connection conn = null;
        PreparedStatement pre =null;
        int flag = 0;
        return 1;
    }

}
