package com.tianmigangwan.service.impl;

import com.alibaba.fastjson.JSON;
import com.sun.deploy.net.HttpResponse;
import com.tianmigangwan.mapper.UserMapper;
import com.tianmigangwan.pojo.CommonResult;
import com.tianmigangwan.pojo.User;
import com.tianmigangwan.service.UserService;
import com.tianmigangwan.utils.MailSender;
import com.tianmigangwan.utils.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        long startTime = System.currentTimeMillis();
        CommonResult result = new CommonResult<>();

        User record = userMapper.queryUserByPhoneNumber(user.getPhoneNumber());//调用mapper接口查询数据库
        log.info("前台传过来的用户信息：" + user);
        log.info("数据库中查到的用户信息：" + record);
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

        long endTime = System.currentTimeMillis();
//        log.info("单线程情况下共耗时"+(endTime-startTime));
        log.info("多线程情况下共耗时" + (endTime - startTime));
        //多线程情况下共耗时168
        //单线程情况下共耗时3441
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

    public static String getAddressByIP(String ip) {
        String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6006&format=json&query=" + ip;
//        JSON.parse
        return null;
    }

}
