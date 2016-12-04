<%--
  Created by IntelliJ IDEA.
  User: chenhansen
  Date: 2016/11/30
  Time: 19:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html xmlns:width="http://www.w3.org/1999/xhtml" xmlns:height="http://www.w3.org/1999/xhtml" xmlns:0px xmlns:0px
      lang="zh-cn">
<%
    int limit;
    int offset;
    try {
        limit = Integer.valueOf(request.getParameter("limit"));
        offset = Integer.valueOf(request.getParameter("offset"));
    } catch (NumberFormatException e) {
        limit = 15;
        offset = 0;
    }
%>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>我的相册</title>
    <script type="text/javascript" src="//code.jquery.com/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="//code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
    <link rel="stylesheet" type="text/css" href="//code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css">

    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" rel="stylesheet">
    <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link href="https://rawgit.com/moxiecode/plupload/master/js/jquery.ui.plupload/css/jquery.ui.plupload.css"
          rel="stylesheet">
    <script src="https://rawgit.com/moxiecode/plupload/master/js/plupload.full.min.js"></script>
    <script src="https://rawgit.com/moxiecode/plupload/master/js/jquery.ui.plupload/jquery.ui.plupload.min.js"></script>

    <style>
        body {
            background-color: #f3f3f3;
        }

        p {
            text-align: center;
        }

        .item {
            display: inline-block;
            border: 1px solid #e7e7e7;
            background-color: #fefefe;
            margin: 5px;
        }

        .item:hover {
            -webkit-box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
            -moz-box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
            box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
        }

        .photo {
            width: 120px;
            height: 120px;
            margin: 15px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="span12">
            <div class="tabbable" id="tabs-323233">
                <ul class="nav nav-tabs">
                    <li class="active">
                        <a id="album" data-toggle="tab" href="#panel-284307">我的相册</a>
                    </li>
                    <li>
                        <a id="upload" data-toggle="tab" href="#panel-948434">上传照片</a>
                    </li>
                </ul>
                <div class="tab-content" style="margin: auto;padding-top: 20px;">
                    <div class="tab-pane active" id="panel-284307">
                        <div id="photos" class="row">
                        </div>
                    </div>
                    <div class="tab-pane" id="panel-948434">
                        <div class="row">
                            <form id="form" method="post" action="" enctype="multipart/form-data">
                                <div id="uploader">
                                    <p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
                                </div>
                            </form>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <nav id="page">
            <ul class="pager">
                <%
                    int prePageOffset = 0;
                    if (offset >= limit) {
                        prePageOffset = offset - limit;
                    }
                    int nextPageOffset = offset + limit;
                    String href = "/album.jsp?limit=" + limit + "&offset=";
                %>
                <%if (prePageOffset < 0) {%>
                <li class="previous"><a href="#">上一页</a></li>
                <%} else {%>
                <li class="previous"><a href='<%=href%><%=prePageOffset%>'>上一页</a></li>
                <%}%>
                <li class="next"><a href='<%=href%><%=nextPageOffset%>'>下一页</a></li>
            </ul>
        </nav>
    </div>
</div>
<script>
    console.log("script:");
    var offset = <%=offset%>;
    var limit = <%=limit%>;
    var hasMore = true;
    function query(limit, offset) {
        $.ajax({
            url: '/album?limit=' + limit + '&offset=' + offset,
            method: "GET",
            async: true,
            dataType: 'json',
            success: function (res, status, xhr) {
                console.log(res);
                if (res.length < limit) {
                    hasMore = false;
                    $("li.next a").attr("href", "#");
                }
                for (var i = 0; i < res.length; i++) {
                    var item = res[i];
//                    var href = "/slide.jsp?limit=" + limit + "&offset=" + (offset + i);
                    var href = "/photo.jsp?file=" + item.replace('/file/', '');
                    $("#photos").append('<div class="item col-md-2"><a href="' + href + '"><img class="photo" src="' + item + '"/></a></div>');
                }
            }
        });
    }

    $(function () {
        console.log("document: ready");
        query(limit, offset);
        $("#next_page").click(function () {
            if (hasMore) {
                offset += 10;
                $("#imglist li").remove();
                query(limit, offset);
            }
        });
        $("#upload").click(function () {
            $("#page").hide();
            $("a.plupload_button.plupload_add").addClass("btn btn-primary").css("color", "#fff !important");
            $("a.plupload_button.plupload_start").addClass("btn btn-primary").css("color", "#fff !important");
            $("#album").click(function () {
                location.reload();
            })
        });
        $("#album").click(function () {
            $("#page").show();
        })
    });

</script>
<script>
    $(function () {
        $("#uploader").plupload({
            // General settings
            runtimes: 'html5,flash,silverlight,html4',

            // Fake server response here
            // url : '../upload.php',
            url: "/file",

            // Maximum file size
            max_file_size: '8mb',

            // User can upload no more then 20 files in one go (sets multiple_queues to false)
            max_file_count: 20,

            chunk_size: '1mb',

            // Resize images on clientside if we can
//            resize: {
//                width: 200,
//                height: 200,
//                quality: 90,
//                crop: true // crop to exact dimensions
//            },

            // Specify what files to browse for
            filters: [
                {title: "Image files", extensions: "jpg,gif,png"},
            ],

            // Rename files by clicking on their titles
            rename: true,

            // Sort files
            sortable: true,

            // Enable ability to drag'n'drop files onto the widget (currently only HTML5 supports that)
            dragdrop: true,

            // Views to activate
            views: {
                list: true,
                thumbs: true, // Show thumbs
                active: 'thumbs'
            },

            // Flash settings
            flash_swf_url: '//cdn.bootcss.com/plupload/3.0-beta1/Moxie.swf',

            // Silverlight settings
            silverlight_xap_url: 'http://rawgithub.com/moxiecode/moxie/master/bin/silverlight/Moxie.cdn.xap'
        });


        $('#uploader_start').click(function () {
            // Files in queue upload them first
            if ($('#uploader').plupload('getFiles').length > 0) {

                // When all files are uploaded submit form
                $('#uploader').on('complete', function () {
//                    $('#form')[0].submit();
                });

                $('#uploader').plupload('start');
            } else {
                alert("You must have at least one file in the queue.");
            }
        })
    });
</script>
</body>
</html>
