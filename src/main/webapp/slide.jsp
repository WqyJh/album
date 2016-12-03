<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-cn" xmlns:width="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>我的相册</title>
    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<%
    int limit;
    int offset;
    try {
        limit = Integer.valueOf(request.getParameter("limit"));
        offset = Integer.valueOf(request.getParameter("offset"));
    } catch (NumberFormatException e) {
        limit = 10;
        offset = 0;
    }
%>
<body>
<div class="container">
    <div class="row-fluid">
        <div class="span12">
            <h3 class="text-center">
                我的相册
            </h3>

            <div>
                <div class="btn-group btn-group-lg col-md-6">
                    <a class="btn btn-lg btn-success col-md-2" href="/album.jsp?limit=<%=limit%>&offset=<%=offset%>">
                        返回</a>
                    <button class="btn btn-lg btn-danger col-md-2 col-md-pull-2">
                        删除</button>
                </div>

            </div>

            <div class="row-fluid">
                <div class="span12">
                    <div class="carousel slide" id="carousel-436047">
                        <ol id="indicators" class="carousel-indicators">
                        </ol>
                        <div id="images" class="carousel-inner" style="width: 100%;height: 80%">
                        </div>
                        <a data-slide="prev" href="#carousel-436047" class="left carousel-control">‹</a> <a
                            data-slide="next" href="#carousel-436047" class="right carousel-control">›</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
    var initOffset = <%=offset%>;
    var initLimit = <%=limit%>;
    var hasMore = true;
    var start = initOffset - initLimit / 2;
    if (start < 0) {
        start = 0;
    }

    function query(limit, offset, append) {
        $.ajax({
            url: '/album?limit=' + limit + '&offset=' + offset,
            method: "GET",
            async: false,
            dataType: 'json',
            success: function (res, status, xhr) {
                console.log(res);
                if (res.length < limit) {
                    hasMore = false;
                }
                if (append) {
                    for (var i = 0; i < res.length; i++) {
                        $("#indicators").append('<li data-slide-to="' + (offset + i) + '" data-target="#carousel-436047"/>');
                        $("#images").append('<div class="item image"> <img class="img-responsive carousel-inner" src="' + res[i] + '"/> <div class="carousel-caption"></div></div>');
                    }
                } else {
                    for (var i = res.length - 1; i >= 0; i--) {
                        $("#indicators").prepend('<li data-slide-to="' + (offset + i) + '" data-target="#carousel-436047"/>');
                        $("#images").prepend('<div class="item image" > <img class="img-responsive carousel-inner" src="' + res[i] + '"/> <div class="carousel-caption"></div></div>');
                    }
                }
            }
        });
    }
    $(document).ready(function () {
        console.log("document: ready");
        query(initLimit, start, true);
        var current = initOffset - start;
        console.log("current: " + current);
        $("#indicators").children().eq(current).addClass('active');
        $("#images").children().eq(current).addClass('active');
    });
</script>
</body>
</html>