package com.tianmigangwan.controller;

import cn.dsna.util.images.ValidateCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @GetMapping("/getValidateCodeImage")
    public void getValidateCode(HttpServletResponse response, HttpSession session) throws IOException {
        ValidateCode validateCode = new ValidateCode(160, 40, 4, 50);
        session.setAttribute("validateCode",validateCode.getCode());
        validateCode.write(response.getOutputStream());
    }

    @GetMapping("/getValidateCode")
    public String getValidateCode(HttpSession session){
        return session.getAttribute("validateCode").toString();
    }
}
