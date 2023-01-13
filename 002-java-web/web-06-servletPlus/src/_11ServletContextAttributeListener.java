import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

public class _11ServletContextAttributeListener implements ServletContextAttributeListener {
    /*
    8.ServletContextAttributeListener接口
             监听全局作用域对象共享数据变化时刻
    9.全局作用域对象共享数据变化
                   ServletContext application = request.getServletContext();
                   application.setAttribute("key1",100); //新增共享数据
                   application.setAttribute("key1",200);//更新共享数据
                   application.removeAttribute("key1");//移除共享数据
    * */
    //数据添加变化调用
    public void attributeAdded(ServletContextAttributeEvent scae) {
        System.out.println("数据添加了");
    }

    //数据删除变化调用
    public void attributeRemoved(ServletContextAttributeEvent scae) {
        System.out.println("数据删除了");
    }

    //数据更新变化调用
    public void attributeReplaced(ServletContextAttributeEvent scae) {
        System.out.println("数据更新了");
    }
}
