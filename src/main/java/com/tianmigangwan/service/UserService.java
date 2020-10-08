package com.tianmigangwan.service;

import com.tianmigangwan.pojo.CommonResult;
import com.tianmigangwan.pojo.User;

import javax.servlet.http.HttpSession;

public interface UserService {

    CommonResult authentication(User user, HttpSession session);


}
