function goto(location) {
    window.location.href=location;
}

function popUpPrompts(message, color) {
    $("#message").text(message).css({
        "color": color
    });
}


let ResponseCode={
    SUCCESS:200,
    FAILURE:444,
    INVALID_VERIFICATION_CODE:405,
    INVALID_PASSWORD:406,
    UNAUTHORIZED_ACCESS:407,
    NO_SPECIFIED_RECORD:408,
    SERVER_EXCEPTION:555,
};