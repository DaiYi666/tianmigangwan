$(function () {
    let result = getUserInformation();
    analyticalResult(result);

    $("#log-out").click(function () {
        popUpPrompts("您确定要退出吗？", "red");
        $("#prompt").modal("show");
    });
});


//发送ajax请求获取session中的用户信息
function getUserInformation() {
    let result = null;

    $.ajax({
        type: "GET",
        url: "/user/getUserInformation",
        async: false,
        dataType: "JSON",
        success: function (data) {
            result = data;
        }
    });
    return result;
}

//分析返回的结果
function analyticalResult(result) {

    if (result.responseCode == ResponseCode.SUCCESS) {
        let user = result.responseBody;
        $("#head-portrait").attr({"src": user.headPortrait});
        $("#nickname").text(user.nickname);
        $("#photo-count").text(user.photoCount);
        $("#video-count").text(user.videoCount);
        return;
    } else if (result.responseCode == ResponseCode.UNAUTHORIZED_ACCESS) {
        popUpPrompts("您还尚未登录，请您先登录", "red");
    } else if (result.responseCode == ResponseCode.SERVER_EXCEPTION) {
        popUpPrompts("服务器正忙，请您稍后再试", "red");
    }
    $("#prompt").modal("show");
}