<%--
  Created by IntelliJ IDEA.
  User: 不是我
  Date: 2021/7/25
  Time: 12:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="js/jquery-3.4.1.js"></script>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript">
        $(function () {

            $("button:eq(0)").click(function () {
                $.ajax(
                    {
                        url:"returnVoid.do",
                        data:{
                            name:"王力",
                            age:55
                        },
                        dataType:"json",
                        success:function (resp) {
                            alert("returnVoid执行"+resp.name+resp.age);
                            $($("input")[0]).val(resp.name);
                            $($("input")[1]).val(resp.age);
                        }
                    }
                )
            })

            $("button:eq(1)").click(function () {
                $.ajax(
                    {
                        url:"returnObject.do",
                        data:{
                            name:"斯金科",
                            age:22
                        },
                        dataType:"json",
                        success:function (resp) {
                            alert("returnObject执行"+resp.name+resp.age);
                            $($("input")[0]).val(resp.name);
                            $($("input")[1]).val(resp.age);
                        }
                    }
                )
            })
            $("button:eq(2)").click(function () {
                $.ajax(
                    {
                        url:"returnList.do",
                        dataType:"json",
                        success:function (resp) {
                            alert(resp[0].name+resp[0].age+"   "+resp[1].name+resp[1].age);
                        }
                    }
                )
            })
            $("button:eq(3)").click(function () {
                $.ajax(
                    {
                        url:"returnStrings.do",
                        dataType:"text",
                        success:function (resp) {
                            alert(resp);
                        }
                    }
                )
            })
        })
    </script>
</head>
<body>
<h2>RequestMapping</h2>
<a href="some.do">我是doSome方法</a>
<a href="other.do">我是doOther方法</a>

<hr/>
<h2>Parameter</h2>
<a href="parameter.do?name=huangjiabin">我是parameter方法</a>
<form action="parameter2.do">
    姓名：<input type="text" name="name">
    年龄：<input type="text" name="age">
    <input type="submit">
</form>
<a href="parameter3.do?name=徐凤年&age=23">我是parameter方法</a><br/>

<hr/>
<h2>Return</h2>
<a href="returnMV.do">我是returnMV</a><br/>
<a href="returnString.do">我是returnString</a><br/>
<button>returnVoid-Ajax</button><br/>
<button>returnObject-Ajax</button><br/>
<button>returnList-Ajax</button><br/>
<button>returnString-Ajax</button>
</body>
</html>
