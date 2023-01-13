<%--
  Created by IntelliJ IDEA.
  User: 不是我
  Date: 2021/6/11
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>ajax</title>
    <script type="text/javascript" src="jquery-3.4.1.js"></script>
    <script type="text/javascript">
      function searchname() {
        //第一步创建异步对象
        var xmlHttp=new XMLHttpRequest();
        //第二步绑定监听器
        xmlHttp.onreadystatechange=function () {
          if(xmlHttp.readyState==4&&xmlHttp.status==200){
            var proname = xmlHttp.responseText;
            document.getElementById("proname").value=proname;
          }
        }
        var id=document.getElementById("proid").value;
        //第三步初始化异步对象
        xmlHttp.open("get","/myweb/sn?proid="+id,true);
        //第四步发送请求
        xmlHttp.send();
      }
      //用jQuery做ajax
      function searchp() {
        var pro=$("#proid2").val();
        $.ajax(
                {
                  url:"/myweb/sp",
                  data:{
                    proid2:pro
                  },
                  dataType:"json",
                  success:function (resp) {
                    alert(resp.name+"==="+resp.jiancheng);
                    $("#one").text(resp.name);
                  }
                }
        )
      }
    </script>
    <script type="text/javascript">
      $(function () {
        $("#btn1").on("click",function () {
          $.ajax({
            url:"san",
            dataType:"json",
            success:function (resp) {
              $("#select1").empty();
              $("#select1").append("<option id='null'>请选择</option>");
              $.each(resp,function (i,n) {
                $("#select1").append("<option value='"+n.id+"'>"+n.name+"</option>");

              })
            }

          })
        })

        $("#select1").change(function () {
          $("#select2").empty();
          $("#select2").append("<option>请选择</option>");
          var id=$("#select1>option:checked").val();
          $.ajax({
            url:"sc",
            data:{
              cid:id,
            },
            dataType:"json",
            success:function (resp) {
              $.each(resp,function (i,n) {

                $("#select2").append("<option>"+n.name+"</option>");
              })
            }

          })
        })
      })
    </script>
  </head>

  <body>
<h2>城市查询</h2>
  <hr>

<table>
  <tr>
    <td>省份编号</td>
    <td><input type="text" id="proid"><input type="button" value="提交" onclick="searchname()">
    </td>
  </tr>
  <tr>
    <td>省份名称</td>
    <td><input type="text" id="proname" readonly="true"></td>
  </tr>
</table>

<table>
  <tr>
    <td>省份编号</td>
    <td><input type="text" id="proid2"><input type="button" value="提交" onclick="searchp()">
    </td>
  </tr>
  <tr>
    <td>省份名称</td>
    <td id="one"></td>
  </tr>
</table>

<hr>
  <h2>联级下拉列表</h2>
  <hr>

  省份:<select id="select1">
<option>请选择</option>
</select>

<button id="btn1">加载省份</button>
<br>

  城市:<select id="select2">
  <option >请选择</option>
</select>
  
  </body>
</html>
