package com.tianmigangwan.service;

import com.tianmigangwan.pojo.CommonResult;
import com.tianmigangwan.pojo.User;

import javax.servlet.http.HttpSession;

public interface UserService {

    /**
     *
     * @param user  接收到的用户对象
     * @param session   此参数用于验证成功后设置session使用
     * @return
     */
    CommonResult authentication(User user, HttpSession session);

    void sendLoginReminderEmail(User user);


}
