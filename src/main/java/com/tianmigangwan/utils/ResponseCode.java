package com.tianmigangwan.utils;

public class ResponseCode {
    public static final int SUCCESS = 200;  //成功

    public static final int FAILURE = 444;  //失败

    public static final int INVALID_VERIFICATION_CODE = 405;  //无效验证码

    public static final int INVALID_PASSWORD = 406;  //无效密码

    public static final int UNAUTHORIZED_ACCESS = 407;  //非法访问

    public static final int NO_SPECIFIED_RECORD = 408;  //没有指定的记录

    public static final int PHONE_NUMBER_ALREADY_EXISTS = 409;  //手机号已经存在了

    public static final int EMAIL_ALREADY_EXISTS = 410;  //邮箱已经存在了

    public static final int SERVER_EXCEPTION = 555;  //服务器异常
}
