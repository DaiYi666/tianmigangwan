package com.tianmigangwan.handler;

import com.tianmigangwan.pojo.CommonResult;
import com.tianmigangwan.utils.ResponseCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public CommonResult defaultExceptionHandler(){
        CommonResult result = new CommonResult();
        result.setResponseCode(ResponseCode.SERVER_EXCEPTION);
        result.setMessage("server exception");
        return result;
    }


}
