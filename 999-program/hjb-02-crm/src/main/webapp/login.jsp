<%--
  Created by IntelliJ IDEA.
  User: 不是我
  Date: 2021/8/7
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="js/jquery-3.4.1.js"></script>
    <script type="text/javascript">
        $(function () {
            if(window.top!=window){
                window.top.location=window.location;
            }

            $("#loginAct").val("");
            $("#loginPwd").val("");
            $("#loginAct").focus();
            
            $(window).keydown(function (event) {
                if(event.keyCode==13){
                    login();
                }
            })
        })
        function login(){
            var loginAct = $("#loginAct").val();
            var loginPwd = $("#loginPwd").val();
            if(loginAct =="" || loginPwd==""){
                $("#msg").html("账号密码不能为空");
                return false;
            }
            $.ajax({
                url:"settings/user/login.do" ,
                data:{
                    "loginAct":loginAct,
                    "loginPwd":loginPwd
                },
                type:"post",
                dataType:"json",
                success:function (data) {
                    if(data.success=="false"){
                        $("#msg").html(data.msg);
                    }else if(data.success="true"){
                        window.location.href="workbench/index.jsp";
                    }
                }
            })
        }
    </script>
</head>
<body>
<h2 align="center">CRM</h2>
    <form>
        <table align="center">
            <tr>
                <td><input type="text" placeholder="姓名" id="loginAct"></td>
            </tr>
            <tr>
                <td><input type="text" placeholder="密码" id="loginPwd"></td>
            </tr>
            <tr><td><span id="msg" style="color: red"></span></td></tr>
            <tr><td><button type="button" id="submitBtn" onclick="login()">登录</button></td></tr>
        </table>
    </form>
</body>
</html>
