console.log("script:");
var offset = 0;
var limit = 10;
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
            }
            for (var i = 0; i < res.length; i++) {
                var item = res[i];
                var href = "/slide.jsp?limit=" + limit + "&offset=" + (offset + i);
                $("#photos").append('<div class="item col-md-2"><a href="' + href + '"><img class="photo" src="' + item + '"/></a></div>');
            }
        }
    });
}

$(document).ready(function () {
    console.log("document: ready");
    query(limit, offset);
    $("#next_page").click(function () {
        if (hasMore) {
            offset += 10;
            $("#imglist li").remove();
            query(limit, offset);
        }
    });
});
