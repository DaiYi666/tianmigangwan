$(function () {

    getUserAccount();

    //登录按钮的点击事件
    $("#login").on("click",function () {
        let userData = getUserData();

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
            $("#validate-code-image").trigger("click");
        } else if (!checkVerificationCode(userData.validateCode)) {
            popUpPrompts("验证码错误！", "red");
            $("#validate-code-image").trigger("click");
        } else {
            let responseCode = authentication(userData);
            console.log(responseCode);
            if (responseCode == ResponseCode.SUCCESS) {
                if ($("#rememberPassword").is(":checked")) {
                    rememberPassword(userData.phoneNumber, userData.password, 7);
                    window.location.replace("/user/homepage.html");
                    return;
                }
            } else if (responseCode == ResponseCode.INVALID_PASSWORD) {
                popUpPrompts("密码错误！", "red");
                $("#validate-code-image").trigger("click");
            } else if (responseCode == ResponseCode.NO_SPECIFIED_RECORD) {
                popUpPrompts("用户不存在！", "red");
                $("#validate-code-image").trigger("click");
            } else if (responseCode == ResponseCode.SERVER_EXCEPTION) {
                popUpPrompts("服务器异常，请稍后再试！", "orange");
                $("#validate-code-image").trigger("click");
            }
        }

        $("#prompt").modal("show");
    });

    //验证码图片的点击事件
    $("#validate-code-image").on("click",function () {
        $(this).attr({"src": "/validateCode/getValidateCodeImage?" + new Date().getTime()});
    });


    $("#register").on("click",function () {
        window.location.replace("/user/userRegister.html");
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


function getUserData() {
    let phoneNumber = $("#phone-number").val();
    let userPassword = $("#password").val();
    let verificationCode = $("#verification-code").val();
    let userData = {"phoneNumber": phoneNumber, "password": userPassword, "verificationCode": verificationCode};
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
        url: "/verificationCode/getImageVerificationCode",
        async: false,
        success: function (result) {
            isSuccess = result.toLowerCase() === validateCode.toLowerCase();
        }
    });

    return isSuccess;
}


function rememberPassword(phoneNumber, password, time) {
    $.cookie("phoneNumber", phoneNumber, {expires: time});
    $.cookie("password", password, {expires: time});
}


function getUserAccount() {
    $("#phoneNumber").val($.cookie("phoneNumber"));
    $("#password").val($.cookie("password"));
}