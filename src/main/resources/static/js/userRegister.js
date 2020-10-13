$(function () {

    let isSend = true; //是否不发送

    let countdown = 10;

    $("#send-email-verification-code").on("click", function () {
        let recipient = $("#email").val();
        if (isSend && isEmailAddress(recipient)) {
            sendEmailVerificationCode(recipient);
            popUpPrompts("验证码已发送！", "green");
            isSend = false;
            setTimeout(function () {
                isSend = true;
            }, countdown * 1000);
        } else if (isSend == false) {
            popUpPrompts("操作频繁，请稍后再试！", "red");
        } else {
            popUpPrompts("请输入正确的邮箱！", "red");
        }

        $("#prompt").modal("show");
    });


    $("#submit").on("click", function () {
        let userData = getUserData();
        if (userData.nickname == "") {
            popUpPrompts("请输入昵称！", "red");
        } else if (userData.phoneNumber == "") {
            popUpPrompts("请输入手机号！", "red");
        } else if (!isPhoneNumber(userData.phoneNumber)) {
            popUpPrompts("请输入正确的手机号！", "red");
        } else if (userData.email == "") {
            popUpPrompts("请输入邮箱地址！", "red");
        } else if (!isEmailAddress(userData.email)) {
            popUpPrompts("请输入正确的邮箱地址！", "red");
        } else if (userData.emailVerificationCode == "") {
            popUpPrompts("请输入验证码！", "red");
        } else if (userData.emailVerificationCode.length < 4) {
            popUpPrompts("请输入正确的验证码！", "red");
        } else if (userData.password == "") {
            popUpPrompts("请输入密码！", "red");
        } else if (userData.password.length < 6) {
            popUpPrompts("密码长度必须在6~18位之间！", "red");
        } else if (userData.confirmPassword == "") {
            popUpPrompts("请再次输入密码！", "red");
        } else if (userData.password.length < 6) {
            popUpPrompts("密码长度必须在6~18位之间！", "red");
        } else if (userData.password != userData.confirmPassword) {
            popUpPrompts("两次输入的密码不一致！", "red");
        }else if (!verificationVerificationCode(userData.emailVerificationCode)) {
            popUpPrompts("验证码错误！", "red");
        } else {
            let responseCode = submit(userData);
            if (responseCode == ResponseCode.SUCCESS) {
                popUpPrompts("注册成功！", "green");
            } else if (responseCode == ResponseCode.FAILURE) {
                popUpPrompts("注册失败！", "red");
            } else if (responseCode == ResponseCode.SERVER_EXCEPTION) {
                popUpPrompts("服务器正忙，请稍后再试！", "yellow");
            }
        }

        $("#prompt").modal("show");
    });


});


function sendEmailVerificationCode(recipient) {
    $.ajax({
        type: "GET",
        data: {"recipient": recipient},
        url: "/verificationCode/sendEmailVerificationCode"
    });
}


function verificationVerificationCode(verificationCode) {
    let isCorrect=false;

    $.ajax({
        type: "GET",
        url: "/verificationCode/getEmailVerificationCode",
        contentType: "application/json",
        async: false,
        success: function (result) {
            console.log("获取到的邮箱验证码是："+result);
            isCorrect=verificationCode == result;
            console.log(verificationCode == result);
        }
    });

    console.log(isCorrect);
    return isCorrect;
}



function submit(userData) {
    let responseCode;

    $.ajax({
        type: "POST",
        url: "/user/register",
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


function getUserData() {
    return {
        "nickname": $("#nickname").val(),
        "phoneNumber": $("#phone-number").val(),
        "gender": $("input[name='gender']:checked").val(),
        "email": $("#email").val(),
        "emailVerificationCode": $("#email-verification-code").val(),
        "password": $("#password").val(),
        "confirmPassword": $("#confirm-password").val()
    };
}



