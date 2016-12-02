<%--
  Created by IntelliJ IDEA.
  User: chenhansen
  Date: 2016/11/29
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

  <title>个人相册账号注册</title>
  <style type="text/css">
    body{background-color:#97CBFF}

    table{background-color:#97CBFF ;height: 200px; position:absolute; top:30%;left:40%;}
    input {
      border-radius:3px;border:1px;solid: #000;
      width:200px;height:30px;
    }
    .a{
      text-align: center;
      font-size: large;
      text-decoration-color: rgba(140, 13, 12, 0.93);
      font-family: Arial;
      letter-spacing: 10px;
    }
  </style>
</head>
<body>
<form action="/register_action" method="post" >
  <table >
    <tr >
      <td class="a" align="center">新用户注册</td>
    </tr>
    <tr >

      <td ><input type="text" name="username"value="enter your name" onfocus="this.value=''"
                  onblur="if(this.value==''){this.value='enter your name'}"></td>
    </tr>
    <tr >
      <td><input  type="text" name="password"value="password" onfocus="if(this.value==defaultValue) 
        {this.value='';this.type='password'}" 
                  onblur="if(!value) {value=defaultValue; this.type='text';}" style="color:#CCC;"></td>
    </tr>

    <tr align="center">
      <td name="zhuce" align="center"> <input type="submit"  style="background-color: aqua;"  value="注册"></td>
    </tr>

  </table>
</form>

</body>
</html>
