<%--
  Created by IntelliJ IDEA.
  User: chenhansen
  Date: 2016/11/30
  Time: 19:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的个人相册</title>

</head>
<style type="text/css">
h1{position: absolute;left:12%;top:10%;}
    body{
        background-image:url(src1.jpg);
        background-size:100%
    }
    .file-box{
        position: absolute;left: 950px;height: 200px;
    }
  .title{
      position: absolute;
      left: 200px;
      height:300px;
      letter-spacing: 30px;
      color: #fafafa;
      letter-spacing: 5px;
      text-shadow: 0px 1px 0px #999, 0px 2px 0px #888, 0px 3px 0px #777, 0px 4px 0px #666,
      0px 5px 0px #555, 0px 6px 0px #444, 0px 7px 0px #333, 0px 8px 7px #001135 }


    input{
        vertical-align:middle; margin:0; padding:0}


    .file-box{ position:relative;width:340px}
    .txt{ height:22px; border:1px solid #cdcdcd; width:180px;}
    .btn{ background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;}
    .file{ position:absolute; top:0; right:80px; height:24px;
        filter:alpha(opacity:0);opacity: 0;width:260px }
</style>
<body>

<iframe style="position: absolute;left: 200px;top:130px;height: 400px;width:970px;border: hidden;"
        src="photos.html"></iframe>
<h1 class="title">我的相册</h1>

<div class="file-box">
    <form  class="form" action="/file" method="post" enctype="multipart/form-data">
        <input type='text' name='textfield' id='textfield' class='txt' />
        <input type='button' class='btn' value='浏览...' />
        <input type="file" name="fileField" class="file" id="fileField"
               onchange="document.getElementById('textfield').value=this.value" />
        <input type="submit" name="submit" class="btn" value="上传" />
    </form>



</body>
</html>
