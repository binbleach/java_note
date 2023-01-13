<%--
  Created by IntelliJ IDEA.
  User: 不是我
  Date: 2021/8/1
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>这里是重定向jsp</h2>
名字：<%=request.getParameter("myName")%>
年龄：${param.myName}
</body>
</html>
