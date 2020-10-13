package com.tianmigangwan.utils;


public class ValidateCodeUtils {

    public static String getValidateCode(int length){
        StringBuffer validateCode = new StringBuffer();
        for (int i = 0; i < length; i++) {
            validateCode.append((int)Math.floor(Math.random()*10));
        }
        return validateCode.toString();
    }
}
