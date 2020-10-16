package com.tianmigangwan.service.impl;

import com.tianmigangwan.mapper.UserMapper;
import com.tianmigangwan.pojo.CommonResult;
import com.tianmigangwan.pojo.User;
import com.tianmigangwan.service.UserService;
import com.tianmigangwan.utils.MailSender;
import com.tianmigangwan.utils.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 代毅
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;


    @Override
    public CommonResult authentication(User user, HttpSession session) {
        CommonResult result = new CommonResult<>();
        User record = userMapper.queryUserByPhoneNumber(user.getPhoneNumber());//调用mapper接口查询数据库
        user.passwordEncryption();

        if (record == null) {
            result.setResponseCode(ResponseCode.NO_SPECIFIED_RECORD);
            result.setMessage("The specified user was not queried");//未查询到指定的用户
        } else if (user.getPassword().equals(record.getPassword())) {
            result.setResponseCode(ResponseCode.SUCCESS);
            result.setMessage("password authentication passed");//密码验证通过
            session.setAttribute("user", record);
            sendLoginReminderEmail(record);
        } else {
            result.setResponseCode(ResponseCode.INVALID_PASSWORD);
            result.setMessage("wrong phone number or password");//手机号或者密码不正确
        }

        return result;
    }

    @Override
    public void sendLoginReminderEmail(User user) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String loginTime = dateFormat.format(date);
        StringBuffer content = new StringBuffer();
        content.append("尊敬的用户：");
        content.append("\n您好！您的账户：");
        content.append(user.getPhoneNumber());
        content.append("已于：");
        content.append(loginTime);
        content.append("成功登录了客户端");
        new MailSender(mailSender, sender, user.getEmail(), "登录提醒", content.toString()).sendEmail();
    }

    @Override
    public CommonResult addUser(User user) {
        CommonResult result = new CommonResult();

        if (isPhoneNumberBinding(user.getPhoneNumber())){
            result.setResponseCode(ResponseCode.PHONE_NUMBER_ALREADY_EXISTS);
            result.setMessage("the phone number has been registered");
        }else if (isEmailBinding(user.getEmail())){
            result.setResponseCode(ResponseCode.EMAIL_ALREADY_EXISTS);
            result.setMessage("the mailbox has been registered");
        }else {
            user.generateId();
            int i = userMapper.addUser(user);
            log.info("用户信息是："+user+"已添加"+i+"条记录");
            result.setResponseCode(ResponseCode.SUCCESS);
            result.setMessage("registered successfully");
        }
        return result;
    }

    @Override
    public boolean isPhoneNumberBinding(String phoneNumber) {
        return userMapper.isPhoneNumberBinding(phoneNumber);
    }

    @Override
    public boolean isEmailBinding(String email) {
        return userMapper.isEmailBinding(email);
    }

}
