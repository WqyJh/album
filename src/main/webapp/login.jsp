<%--
  Created by IntelliJ IDEA.
  User: chenhansen
  Date: 2016/12/1
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<html>
<head>

    <title>个人相册登陆</title>
    <style type="text/css">
        body{background-image:url(src2.jpg);}
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
    <script type="text/javascript">
function check() {

    if(request.getAttribute("message").equals("1")){
        alert("用户名或密码错误！")
    }
}
    </script>
</head>
<body onload="check()">

<form action="/login_action" method="post" >
    <table >
        <tr >
            <td class="a" align="center">欢迎登陆</td>
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
            <td name="denglu" > <input type="submit"  style="background-color: aqua;"  value="登陆"></td>
            <br><td name="zhuce" ><a href="/register.jsp" >注册</a></td>
        </tr>

    </table>
</form>

</body>
</html>


