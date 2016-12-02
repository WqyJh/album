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
            res.forEach(function (item) {
                $("#imglist").append('<li><img width="50px" height="50px" src="' + item + '"/></li>');
            })
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
