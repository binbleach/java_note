package control;

import com.mysql.cj.protocol.Resultset;
import util.JdbcUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class listen implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //数据连接池管理方案
        //在全局作用域对象ServletContext创建时先建他一池的连接,要用的时候再调用
        Map map = new ConcurrentHashMap();
        JdbcUtil util = new JdbcUtil();
        for(int i=0;i<20;i++) {
            Connection con=util.getcon();
            System.out.println("启动时创建connection"+con);
            map.put(con,true);
        }
        ServletContext application=sce.getServletContext();
        application.setAttribute("conPool",map);
    }

    @Override
    //这里普及一下map set 迭代器 Iterator
    public void contextDestroyed(ServletContextEvent sce) {
        //拿到全局作用域对象
        ServletContext application=sce.getServletContext();
        //拿到map集合
        Map map=(Map)application.getAttribute("conPool");
        //拿到迭代器 .keySet()是将 map 的 key 部分取出来 map集合转换成set集合的意思,
        // 然后就可以用迭代器了,迭代的也是key部分
        Iterator it=map.keySet().iterator();
        while (it.hasNext()){  //hasNext()看下一行有没有数据
            //有的话进来,next()走下一行,然后取。
            Connection con=(Connection) it.next();
            if(con!=null){
                try {
                    System.out.println(con+"哥二十年后还是一条好汉");
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
