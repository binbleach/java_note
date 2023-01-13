<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Emp" %><%--
  Created by IntelliJ IDEA.
  User: 不是我
  Date: 2021/6/6
  Time: 23:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table border="2px" align="center">
<tr>
    <td><input type="checkbox">全选/全不选</td>
    <td>职员姓名</td>
    <td>职员密码</td>
    <td>职员年龄</td>
    <td>职员工作</td>
    <td>职员工资</td>
</tr>

<%
    List<Emp> list= (List) request.getAttribute("emp");
    for(Emp emp:list){
%>
    <%--这里有个弊端就是el不能参与循环，所以就引入了jstl--%>
<tr>
    <td><input type="checkbox">全选/全不选</td>
    <td><%=emp.getuserName()%></td>  <%--不能有分号，还有空格--%>
    <td><%=emp.getPassword()%></td>
    <td><%=emp.getAge()%></td>
    <td><%=emp.getJob()%></td>
    <td><%=emp.getSal()%></td>
</tr>
<%
    }

%>
</table>