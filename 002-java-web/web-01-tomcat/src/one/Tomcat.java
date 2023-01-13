package one;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Tomcat {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Map m = new HashMap();
        m.put("/one","one.OneServlet");        //map集合用来存真名和对外的名字

        System.out.println("请输入你的url");
        Scanner url = new Scanner(System.in); //控制台模拟客户端发起请求
        String uri = url.next();//获取控制台输入得字符串
        System.out.println("请输入你的请求方式");
        Scanner method = new Scanner(System.in);
        String md = method.next();
        System.out.println(md);
        String ur = uri.substring(uri.lastIndexOf("/"));//根据url中文件名称定位被访问Servlet接口实现类。

        Class obj=Class.forName((String)m.get(ur));     //Tomcat通过Class.forName方法将硬盘类文件加载到内存

        Servlet inf=(Servlet)obj.newInstance();         //Tomcat通过反射机制通知JVM创建当前类的实例对象

        inf.init();                                 //Tomcat通过实例对象调用init方法来实现对实例对象初始化操作

        inf.service(md);                            //Tomcat通过实例对象调用service方法处理当前用户请求
    }
}
