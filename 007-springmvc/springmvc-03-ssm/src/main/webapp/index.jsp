<%--
  Created by IntelliJ IDEA.
  User: 不是我
  Date: 2021/7/31
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName()+":"+
                        request.getServerPort()+"/"+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>"/>
</head>
<body>
    <div align="center">
        <img style="width: 200px;height: 200px" src="images/meimei.jpg"/>
        <table>
            <tr>
                <a href="addStudent.jsp">学生注册</a>
            </tr>
            <tr>
                <a href="queryStudent.jsp">学生查询</a>
            </tr>
            <tr><a href="student/requestDispatcher.do">请求转发</a></tr>
            <tr><a href="student/sendRedirect.do">重定向</a></tr>
        </table>
    </div>
    <form action="student/exception.do">
        后台登陆名:<input type="text" name="name">
        年龄：<input type="text" name="age">
        <input type="submit" >
    </form>
    <button onclick="toCs1Page()" type="button" >跳转到上传测试</button>
</body>
<script>
    function toCs1Page() {
        console.log("toCs1Page...")
        window.location="uploadController/uploadController.do"
    }
</script>
</html>
