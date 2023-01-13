<%--
  Created by IntelliJ IDEA.
  User: 不是我
  Date: 2021/8/1
  Time: 0:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="js/jquery-3.4.1.js"></script>
    <script type="text/javascript">
        $(function () {
            loadStudents();
            $("button").click(function () {
                    loadStudents();
            })
        })
        function loadStudents() {
            $.ajax({
                url:"student/queryStudents.do",
                type:"get",
                data:"json",
                success:function (data) {
                    $("#info").html("");
                    $.each(data,function (i,n) {
                        $("#info").append("<tr>")
                            .append("<td>"+n.id+"</rd>")
                            .append("<td>"+n.name+"</rd>")
                            .append("<td>"+n.age+"</rd>")
                            .append("</tr>");
                    })
                }
            })
        }
    </script>
</head>
<body>
    <div align="center">
        <p>学生信息</p>
        <table border="2px">
            <thead>
                <tr>
                    <td>id</td>
                    <td>姓名</td>
                    <td>年龄</td>
                </tr>
            </thead>
            <tbody id="info">

            </tbody>
        </table>
        <button>查询学生信息</button>
    </div>
</body>
</html>
