<%--
  Created by IntelliJ IDEA.
  User: 不是我
  Date: 2021/7/8
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <body>
  <%--
    文件上传对表单的要求
    1、表单中请求提交方式必须是post
    2、表单中应指定所提交的数据编码格式 enctype=multipart/form-data
    3、表单中要有type="file"的输入框
  --%>
  <form action="fileup"method="post" enctype="multipart/form-data">
    名字<input type="text" name="name"><br>
    年龄<input type="text" name="age"><br>
    照片<input type="file" name="photo"><br>
    <input type="submit" value="提交">
  </form>

  <form action="fileUploadServlet"method="post" enctype="multipart/form-data">
    名字1<input type="text" name="name"><br>
    年龄1<input type="text" name="age"><br>
    照片1<input type="file" name="photo"><br>
    视频<input type="file" name="video"><br>
    <input type="submit" value="提交111">
  </form>
  </body>
  </body>
</html>
