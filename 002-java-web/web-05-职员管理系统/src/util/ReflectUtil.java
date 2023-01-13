package util;

import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.x.protobuf.MysqlxExpr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;

public class ReflectUtil {
    public static Object init(Class classFile, HttpServletRequest request){
        Field fieldArray[] = null;
        Object instance =null;
        try {
            //获取类中所有属性
            fieldArray=classFile.getDeclaredFields();
            //创建类用的是无参构造方法
            instance=classFile.newInstance();
            //取一个个属性
            for(Field field:fieldArray){
                //获取属性名根据属性名获得请求参数值
                String value=request.getParameter(field.getName());
                //获得属性类型
                String type=field.getType().getSimpleName();
                //设置可以改
                field.setAccessible(true);
                if(type.equals("int")){
                    //把请求参数值设置成属性值
                    field.set(instance,Integer.valueOf(value));   //数据类型转换
                }else if(type.equals("double")){
                    field.set(instance,Double.valueOf(value));
                }else if(type.equals("String")){
                    field.set(instance,value);
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //返回类
        return instance;
    }
    //这里反射机制插入语句
    public static int insert(Object obj,HttpServletRequest req){
        Field fieldArray[]=null;
        StringBuffer sql = new StringBuffer();    //字符串拼接
        StringBuffer ass = new StringBuffer();
        StringBuffer ayy = new StringBuffer();
        Class classfile=obj.getClass();
        sql.append("insert"+" "+"into"+" "+classfile.getSimpleName());
        fieldArray=classfile.getDeclaredFields();
        ass.append("(");
        for(Field field:fieldArray){
            String fieldName=field.getName();
            if(!ass.toString().equals("(")){
                ass.append(",");
            }
            ass.append(fieldName);
        }
        sql.append(ass);
        sql.append(")"+" "+"values"+" ");
        ayy.append("(");
        for(Field field:fieldArray){
            field.setAccessible(true);
            String fielName=field.getName();
            if(!ayy.toString().equals("(")){
                ayy.append(",");
            }
            try {
                ayy.append("'");
                ayy.append(field.get(obj));
                ayy.append("'");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        ayy.append(")");
        sql.append(ayy);
        //拿通道
        JdbcUtil util = new JdbcUtil();
        PreparedStatement car =util.getcar(sql.toString(),req);
        int flag =0;
        try {
            flag =car.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(sql);
        return flag;
    }
    //反射机制查询语句 主要是ResultSet表转成实体对象
    public static List select(Class classfile,HttpServletRequest req) throws IllegalAccessException, InstantiationException, SQLException {
        //查询应该是查询所有返回集合，集合里存着一条条emp
        String sql = "select * from emp";
        ResultSet re =null;
        PreparedStatement pre =null;
        Field fieldArray[]=classfile.getDeclaredFields();
        List list = new ArrayList();
        JdbcUtil car = new JdbcUtil();
        pre=car.getcar(sql,req);
        re=pre.executeQuery();
        ResultSetMetaData rsmd=re.getMetaData();//获得标题行的数据，不过是大写得转成小写
        while (re.next()){
            Object instance=classfile.newInstance();
            for(int i=1;i<=rsmd.getColumnCount();i++) { //下标是从1开始了
                for(Field field :fieldArray){
                    System.out.println(field.getName());
                    if(field.getName().equalsIgnoreCase(rsmd.getColumnName(i))){
                        field.setAccessible(true);
                        //从数据库select返回得表中取值
                        String value=re.getString(field.getName());
                        if(field.getType().getSimpleName().equals("int")){
                            field.set(instance,Integer.valueOf(value));
                        }else if(field.getType().getSimpleName().equals("String")){
                            field.set(instance,String.valueOf(value));
                        }else if(field.getType().getSimpleName().equalsIgnoreCase("Double")){
                            field.set(instance,Double.valueOf(value));
                        }
                        System.out.println("---------------");
                        System.out.println(Modifier.toString(field.getModifiers()));
                    }
                }
            }
            list.add(instance);
        }
        return list;
    }
}
