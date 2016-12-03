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
    <title>个人相册登陆</title>
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
        .a{
            text-align: center;
            font-size: large;
            text-decoration-color: rgba(140, 13, 12, 0.93);
            font-family: Calibri;

        }
        .zhuce{
            position: absolute;right:5%;top:5%;
        }
       .title{
           position: absolute;
           left: 46%;
           top:10%;
       }
       .register{
           position: absolute;

           left:40%;
           top:60%;
       }
       .sign:focus{
           text-decoration: none;
           border-color:#51a7e8 ;
           outline: none;
           box-shadow: 0px 0px 5px rgba(81,167,232,0.5);
       }
       .count{

           height: 50px;
           width: 300px;
           text-align: center;
           border: 2px solid #ccc;
           border-radius: 10px;
       }
    </style>

</head>
<body>
<div class="title"><h2>Qi Qi Album</h2></div>

<form clas="form" action="/login_action" method="post" >

        <div clas="div1">
        <label class="login_field">Username</label>
            <input type="text" class="input-block" name="username" >
            <label class="password">Password</label>
            <input type="password"class="input-block" name="password" >
            <input type="submit"class="sign"  style="background-color: #55a532; color: white;"  value="Sign in" >
        </div>
</form>
<div class="register">
    <p class="count">New to QiQiAlbum?<a href="/register.jsp">Create a count</a></p>
</div>

</body>
</html>

