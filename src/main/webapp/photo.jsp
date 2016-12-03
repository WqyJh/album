<%--
  Created by IntelliJ IDEA.
  User: wqy
  Date: 16-12-4
  Time: 上午1:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查看照片</title>
    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<form id="back" action="/album.jsp" method="get">
</form>
<div class="container">
    <div class="row">
        <a href="album.jsp" id="delete" class="btn btn-lg btn-danger col-md-4 col-md-push-4">
            删除
        </a>
    </div>
    <div class="row">
        <div class="center-block">
            <div class="img-responsive">
                <%
                    String file = request.getParameter("file");
                    String src = "/file/" + file;
                %>
                <img id="image" src="<%=src%>">
            </div>
        </div>
    </div>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
    $(function () {
        $("#delete").click(function () {
            $.ajax({
                url: '<%=src%>',
                method: "delete",
                success: function () {
                    $("#form").submit();
                }
            })
        })
    })

</script>
</body>
</html>
