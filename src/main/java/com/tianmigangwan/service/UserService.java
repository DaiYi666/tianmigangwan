package com.tianmigangwan.service;

import com.tianmigangwan.pojo.CommonResult;
import com.tianmigangwan.pojo.User;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;

public interface UserService {

    /**
     *
     * @param user  接收到的用户对象
     * @param session   此参数用于验证成功后设置session使用
     * @return
     */
    CommonResult authentication(User user, HttpSession session);

    /**
     * 发送登录提示邮件
     * @param user
     */
    void sendLoginReminderEmail(User user);


    /**
     * 添加一个用户
     * @param user
     * @return  返回数据库中添加了几条记录
     */
    CommonResult addUser(User user);


    /**
     * 判断数据库中是否存在指定的手机号
     * @param phoneNumber   手机号
     * @return  true表示这个手机号已经存在了，false表示不存在
     */
    boolean isPhoneNumberBinding(String phoneNumber);

    /**
     *
     * @param email 邮箱
     * @return  true表示这个邮箱已经存在了，false表示不存在
     */
    boolean isEmailBinding(String email);

}
