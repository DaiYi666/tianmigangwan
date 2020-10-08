$(function () {

    getUserAccount();

    //登录按钮的点击事件
    $("#login").click(function () {
        let userData = getUserData();

        console.log(checkVerificationCode(userData.validateCode));

        if (userData.phoneNumber == "") {
            popUpPrompts("请输入手机号！", "red");
        } else if (!isPhoneNumber(userData.phoneNumber)) {
            popUpPrompts("手机号不正确！", "red");
        } else if (userData.password == "") {
            popUpPrompts("请输入密码！", "red");
        } else if (userData.password.length < 6) {
            popUpPrompts("密码不正确！", "red");
        } else if (userData.validateCode == "") {
            popUpPrompts("请输入验证码！", "red");
        } else if (userData.validateCode.length < 4) {
            popUpPrompts("验证码不足4位数！", "red");
            $("#validateCodeImage").trigger("click");
        } else if (!checkVerificationCode(userData.validateCode)) {
            popUpPrompts("验证码错误！", "red");
            $("#validateCodeImage").trigger("click");
        } else {
            let responseCode = authentication(userData);
            if (responseCode == 200) {
                if ($("#rememberPassword").is(":checked")) {
                    rememberPassword(userData.phoneNumber, userData.password, 7);
                    goto("https://www.baidu.com");
                    return;
                }
            } else if (responseCode == 406) {
                popUpPrompts("密码错误！", "red");
                $("#validateCodeImage").trigger("click");
            } else if (responseCode == 555) {
                popUpPrompts("服务器异常，请稍后再试！", "orange");
                $("#validateCodeImage").trigger("click");
            }
        }

        $("#prompt").modal("show");
    });

    //验证码图片的点击事件
    $("#validateCodeImage").click(function () {
        $(this).attr({"src": "/validateCode/getValidateCodeImage?" + new Date().getTime()});
    });


});

//$(document).ready(function() {
//  document.getElementById("loginBntId").onclick = function() {
//      $('#loginModalId').modal('show');
//  }
////  document.getElementById("loginModalYesId").onclick = function() {
////      $('#loginModalId').modal('hide');
////      alert("登录功能未实现！");
////  }
//});

function isPhoneNumber(phoneNumber) {
    let rule = /^1[3456789]\d{9}$/;
    if (rule.test(phoneNumber)) {
        return true;
    }
    return false;
}

function getUserData() {
    let phoneNumber = $("#phoneNumber").val();
    let userPassword = $("#password").val();
    let validateCode = $("#validateCode").val();
    let userData = {"phoneNumber": phoneNumber, "password": userPassword, "validateCode": validateCode};
    return userData;
}


function authentication(userData) {
    let responseCode = 0;
    $.ajax({
        type: "POST",
        url: "/user/authentication",
        data: JSON.stringify(userData),
        contentType: "application/json",
        processData: false,
        async: false,
        dataType: "JSON",
        success: function (result) {
            responseCode = result.responseCode;
        }
    });
    return responseCode;
}


function checkVerificationCode(validateCode) {
    let isSuccess = false;
    $.ajax({
        type: "GET",
        url: "/validateCode/getValidateCode",
        async: false,
        success: function (result) {
            isSuccess = result.toLowerCase() === validateCode.toLowerCase();
        }
    });

    return isSuccess;
}


function popUpPrompts(message, color) {
    $("#message").text(message).css({
        "color": color
    });
}


function rememberPassword(phoneNumber, password, time) {
    $.cookie("phoneNumber", phoneNumber, {expires: time});
    $.cookie("password", password, {expires: time});
}



function getUserAccount() {
    $("#phoneNumber").val($.cookie("phoneNumber"));
    $("#password").val($.cookie("password"));
}