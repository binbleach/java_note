<%--
  Created by IntelliJ IDEA.
  User: 不是我
  Date: 2021/7/25
  Time: 12:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--动态获取base--%>
<%
    String basePath = request.getScheme() + "://" +
                    request.getServerName() + ":" + request.getServerPort()+
                    request.getContextPath() + "/";
%>
<html>
<head>
    <title>Title</title>
    <%--base自定义，只针对当前页有效--%>
    <base href="<%=basePath%>"/>

</head>
<body>
<%=basePath%>
<h2>RequestMapping</h2>

<%--
    重定向一定是get请求，请求转发看之前的请求是什么就是什么。
    html向servlet的请求既不是请求转发，也不是重定向
    html向servlet,浏览器到服务器的请求和重定向类似，都是 ”/“即服务器根路径。不包含上下文
    jsp底层是servlet...所有以能做请求转发
 --%>
服务器：servlet。浏览器：html<br>
测试服务器到浏览器是否有get、post反应，结果没反应<br>
测试浏览器到服务器是否有get、post反应，结果有反应<br>
测试服务器到服务器是否有get、post反应，结果有反应<br>
推算，服务器到浏览器之间有get、post翻译，中间必定有服务器对其反应。结论security的main有get请求拦截，toMain有post请求拦截<br>
<form action="some.do" method="GET">
    <button type="submit">POST请求some.do然后重定向普通页面</button>可以
</form>
<form action="some2.do" method="POST">
    <button type="submit">POST请求some2.do然后重定向到GET</button>报错不支持post和security那里一模一样
</form>
<form action="some3.do" method="POST">
    <button type="submit">POST请求some3.do然后请求转发</button>报错不支持POST
</form>

测试分别GET请求、POST请求能不能访问百度
<form action="http://www.baidu.com" method="GET"> GET请求可以访问百度
    <button type="submit">GET提交</button>
</form>
<form action="http://www.baidu.com" method="POST">POST请求不能访问百度
    <button type="submit">POST提交</button>
</form>

区别GET、POST能不能直接访问test.html页面。
<form action="test.html" method="GET"><%--http://localhost:8080/myweb/test.html?没问题--%>
    <button type="submit">GET->Html</button>
</form>
<form action="test.html" method="POST"><%--http://localhost:8080/myweb/test.html没问题--%>
    <button type="submit">POST->Html</button>
</form>

<form action="test1/test2.html" method="POST"><%--http://localhost:8080/test.html报错--%>
    <button type="submit">POST->/test1/test2.html</button>
</form>

<h2>
    总结：<br>
    1、前端页面发起的请求既不是请求转发，也不是重定向。<br>
    2、前端页面访问的 ”/“代 表服务器根路径，也就是没有应用名、上下文的概念：http://localhost:8080/<br>
    3、网上说：请求转发路径的 "/" 是 http://localhost:8080/myweb/    （myweb是上下文）<br>
    重定向路径的   ”/“ 是 http://localhost:8080/<br>
</h2>

</body>
</html>
