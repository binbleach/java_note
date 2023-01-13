<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <input id="upload" type="file" accept="image/*" onchange="fnUpload()" multiple />
    <button onclick="fnSubmit()">确认提交</button>
</head>
<body>

</body>
<script>
    $(function () {
        
    })
    function toCs1Page() {
        console.log("toCs1Page...")
        window.location=""
    }
    //可以操作上传文件方法
    function fnUpload(){
        console.log("file====",this)
    }
    function fnSubmit() {

    }
</script>
</html>
