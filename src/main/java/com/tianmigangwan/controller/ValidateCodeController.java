package com.tianmigangwan.controller;

import cn.dsna.util.images.ValidateCode;
import com.tianmigangwan.utils.MailSender;
import com.tianmigangwan.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/verificationCode")
public class ValidateCodeController {

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @GetMapping("/getVerificationCodeImage")
    public void getValidateCode(HttpServletResponse response, HttpSession session) throws IOException {
        ValidateCode validateCode = new ValidateCode(160, 40, 4, 50);
        session.setAttribute("validateCode",validateCode.getCode());
        validateCode.write(response.getOutputStream());
    }

    @GetMapping("/getImageVerificationCode")
    public String getValidateCode(HttpSession session){
        return session.getAttribute("validateCode").toString();
    }


    @GetMapping("/sendEmailVerificationCode")
    public void sendEmailValidateCode(@RequestParam("recipient") String recipient,HttpSession session){
        String validateCode = ValidateCodeUtils.getValidateCode(6);
        log.info("接受到的邮箱地址是："+recipient);
        StringBuffer content = new StringBuffer();
        content.append("尊敬的用户：\n");
        content.append("您好！");
        content.append("您本次注册用户的验证码是：");
        content.append(validateCode);
        content.append("，如非本人操作，请忽略。");
        session.setAttribute("emailVerificationCode",validateCode);
        new MailSender(mailSender,sender,recipient,"邮箱验证",content.toString()).sendEmail();
    }



    @GetMapping("/getEmailVerificationCode")
    public String getEmailVerificationCode(HttpSession session){
        return session.getAttribute("emailVerificationCode").toString();
    }




}
