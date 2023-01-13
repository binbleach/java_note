<%--
  Created by IntelliJ IDEA.
  User: 不是我
  Date: 2021/7/31
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName()+":"+
            request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <title>Title</title>
    <base href="<%=basePath%>"/>
</head>
<body>
    <div align="center">
        <P>学生注册</P>
        <form action="student/addStudent.do">
            <table>
                <tr>
                    <td>姓名</td>
                    <td><input type="text" name="name"></td>
                </tr>
                <tr>
                    <td>年龄</td>
                    <td><input type="text" name="age"></td>
                </tr>
                <tr>
                    <td><button type="submit">提交</button> </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>
