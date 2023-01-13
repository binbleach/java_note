package com.huangjiabin.mimissm.listener;

import com.huangjiabin.mimissm.mapper.ProductTypeMapper;
import com.huangjiabin.mimissm.pojo.ProductType;
import com.huangjiabin.mimissm.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;
//ServletContextListener接口：
//              监听全局作用域对象生命周期变化时刻（和spring容器用的是同一个，
//              不过不能保证谁通过先注册，所有就有那个先加载的概念，不过下面这个是手动获取不用spring容器也就不用设置谁先加载）

@WebListener
public class ProductTpyeListener implements ServletContextListener {
    @Override//全局作用域对象初始化的时候调用
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //手动获取ProductTypeService对象，然后调用方法从dao层取类型
        ApplicationContext applicationContext =new ClassPathXmlApplicationContext("applicationContext.xml");
        ProductTypeService productTypeService=(ProductTypeService) applicationContext.getBean("productTypeServiceImpl");
        List<ProductType> list=productTypeService.getAll();
        //将获取到的类型放在全局作用域对象，方便使用
        servletContextEvent.getServletContext().setAttribute("selectCondition",list);

    }

    @Override//全局作用域对象将要被销毁的时候调用
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
