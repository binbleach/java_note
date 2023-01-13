package com.huangjiabin.com.handler;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    private Long btime;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("MyInterceptor我的拦截器执行-------preHandler()");
        btime=System.currentTimeMillis();
        //这里也可以做请求转发
        String pass=(String) request.getSession().getAttribute("pass");
//        if(pass!=null&&pass.equals("ok")){
//            return true;
//        }else {
//            request.getRequestDispatcher("/tips.jsp").forward(request,response);
//            return false; //返回false的话截止请求。
//        }
        return true;
    }

    @Override
    /*
    *特有的ModelAndView mv 参数 能够改数据也能够改视图
    * */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("MyInterceptor我的拦截器执行-------postHandler()");
        //能够改数据也能够改视图，用的比较少
    }

    @Override
    /*
    * preHandler返回真的话afterCompletion必须执行
    * */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        System.out.println("MyInterceptor我的拦截器执行-------afterCompletion()");
        Long etime = System.currentTimeMillis();
        System.out.println("功能执行时间===="+(etime-btime)); //计算程序运行时间
    }
}
