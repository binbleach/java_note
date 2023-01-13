public class _02状态码 {
    /*Http状态码
            1.介绍：
                       1）由Http服务器生成一个由三位数字组成符号
                       2) 表示本次通信的状态同时可以要求浏览器接收到
                           响应包之后行为
                       3）分为5个大类 （1XX,2xx,3xx,4xx,5xx）
           2.  200： Http服务器将浏览器请求的资源文件进行了返回
           3.  302：Http服务器通知浏览器到响应头中location中读取请求地址
                          并要求浏览器将得到请求地址更新到浏览器地址栏
                          response.sendRedirect("请求地址")
          4. 404： Http服务器通知浏览器本次索要的资源文件不存在
                         原因：
                          1）人为原因：
                                                网站名不对
                                                如果网站名正确，可能资源文件名称
                                                tomcat没有在网站定位默认欢迎资源文件
                                                设置请求地址没有按照uri格式(/网站名/资源文件名)设置
                          2）IEDA没有将最新网站内容交给tomcat
         5.405:  Http服务器通知浏览器本次索要的资源文件已经被找到了，但是
                      这个资源文件对于浏览器请求方式无法接收
                                    get
                      浏览器------------》servlet.doGet()
                                    post
                      浏览器-----------》 servlet.doPost()
        5.500：Http服务器通知浏览器本次索要的资源文件已经被找到了并且
                      也可以接收浏览器的请求方式，但是由于资源文件在处理过程
                     出现了异常因此无法提供服务
                     异常：并不是命令写错了，命令使用数据不满足条件
                     ***一个异常至少遇到三次以上才有可能进行解决
        常见异常:
                一。jdbc-jar包忘记添加
                       ClassNotFoundException: com.mysql.jdbc.Driver

                二。mysql服务器停止或则mysql服务器所在计算机ip地址写错
                      com.mysql.jdbc.exceptions.jdbc4.CommunicationsException: Communications link failure

                三。访问数据库不存在
                     com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Unknown database 'xxx'
                四。访问数据库使用密码不正确
java.sql.SQLException: Access denied for user 'root'@'localhost' (using password: YES)

                五。SQL命令书写错误
com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'dname='888',loc='888' where deptno=5'

                六。NullPointerException
    * */
}
