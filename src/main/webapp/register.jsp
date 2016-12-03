<%--
  Created by IntelliJ IDEA.
  User: chenhansen
  Date: 2016/12/3
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>个人相册账号注册</title>
  <style>
    body{background-color: #f9f9f9}
    form{background-color:#fff ;height: 260px; width:300px;border-radius: 5px;border:2px solid #ccc;
      position:absolute; top:20%;left:40%;}
    input {
      align:center;
      margin-left: 20px;
      margin-top: 10px;
      background-color: white;
      border-radius:5px;border:2px solid #ccc ;
      width:260px;height:40px;
      padding-left: 10px;
    }
    input:hover:focus {
      border-color: #51a7e8;
    }

    label{
      display: block;
      margin-top: 7px;
      margin-left: 20px;
      margin-bottom: 5px;
      font-weight: normal;
    }
    .input-block{
      margin-top: 5px;
      margin-bottom: 15px;
    }
    .input-block:focus{
      border-color: #51a7e8;
      outline: none;
      box-shadow: inset 0px 1px 2px rgba(0,0,0,0.075), 0px 0px 5px rgba(81,167,232,0.5) ;
    }


    .title{
      position: absolute;
      left: 46%;
      top:10%;
    }

    .sign:focus{
      text-decoration: none;
      border-color:#51a7e8 ;
      outline: none;
      box-shadow: 0px 0px 5px rgba(81,167,232,0.5);
    }

  </style>

</head>
<body>
<div class="title"><h2>Qi Qi Album</h2></div>

<form clas="form" action="/register_action" method="post" >

  <div clas="div1">
    <label class="login_field">Username</label>
    <input type="text" class="input-block" name="username" >
    <label class="password">Password</label>
    <input type="password"class="input-block" name="password" >
    <input type="submit"class="sign"  style="background-color: #55a532; color:white"  value="Register" >
  </div>
</form>

</body>
</html>

