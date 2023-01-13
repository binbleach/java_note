package _package.entity;
/*
* 基于特征的封装，就是实体类entity可以方便基于流程的封装
* 下一个就是基于功能的封装,也就是工具类。
* */

public class dept {
    private int deptno;
    private String dname;
    private  String loc;

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public int getDeptno() {
        return deptno;
    }

    public String getDname() {
        return dname;
    }

    public String getLoc() {
        return loc;
    }

    public dept(int deptno, String dname, String loc) {
        this.deptno = deptno;
        this.dname = dname;
        this.loc = loc;
    }
}
