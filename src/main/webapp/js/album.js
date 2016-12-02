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
            if (res == null || res.length <= 0) {
                hasMore = false;
                return;
            }
            res.forEach(function (item) {
                $("#imglist").append('<li><img src="' + item + '"/></li>');
            })
        }
    });
}

$(document).ready(function () {
    console.log("document: ready");
    query(limit, offset);
    $("#next_page").onclick(function () {
        if (hasMore) {
            limit += 10;
            query(limit, offset);
        }
    });
});