function goto(location) {
    window.location.replace(location);
}

function popUpPrompts(message, color) {
    $("#message").text(message).css({
        "color": color
    });
}

function isPhoneNumber(phoneNumber) {
    let rule = /^1[3456789]\d{9}$/;
    return rule.test(phoneNumber) ? true : false;
}


function isEmailAddress(emailAddress) {
    let rule = /^([a-zA-Z]|[0-9])(\w|\-)+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
    return rule.test(emailAddress) ? true : false;
}


let ResponseCode = {
    SUCCESS: 200,
    FAILURE: 444,
    INVALID_VERIFICATION_CODE: 405,
    INVALID_PASSWORD: 406,
    UNAUTHORIZED_ACCESS: 407,
    NO_SPECIFIED_RECORD: 408,
    PHONE_NUMBER_ALREADY_EXISTS: 409,
    EMAIL_ALREADY_EXISTS: 410,
    SERVER_EXCEPTION: 555,
};