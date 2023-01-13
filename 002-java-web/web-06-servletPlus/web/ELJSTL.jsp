<%--
  Created by IntelliJ IDEA.
  User: 不是我
  Date: 2021/6/7
  Time: 23:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>  <%--jsp的设置--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    <%--Jstl的设置--%>

<%--set标签--%>
<c:set scope="session" var="key" value="10"/><%--设置作用域对象中的值--%>
${sessionScope.key} <%--输出的是十--%>
<%--代替下面这段jsp--%>
<%
    session.setAttribute("key2","20");
    String a= (String) session.getAttribute("key2");
%>
<%=a%>
<br/>

<%--EL表达式和 JSTL 配合--%>
<c:set scope="session" var="age" value="10"/>
<c:set scope="session" var="age" value="${sessionScope.age+10}"/>
十年后它的年龄是：${sessionScope.age}
<br>

<%--if表达式--%>
<c:if test="${sessionScope.age ge 9}">
    <font color="red">欢迎光临</font>
</c:if>
<br>

<%--多分支判断--%>
<c:set scope="session" var="sal" value="10000"/>
<c:choose>
    <c:when test="${sal>=10000}">高薪</c:when>
    <c:when test="${sal<10000}">一般</c:when>
    <c:when test="${sal<5000}">低薪</c:when>
</c:choose>
<br>

<%--forEach第一种用法--%>
<select>
    <c:forEach var="i" begin="1" end="5" step="1">
        <option>第${pageScope.i}</option>
    </c:forEach>
</select>
<br>

<%--第二种更像forEachf可以遍历集合--%>
<c:forEach items="${requestScope.stu}" var="st">
    ${st.age}
    ${st.name}
    <br/>
</c:forEach>