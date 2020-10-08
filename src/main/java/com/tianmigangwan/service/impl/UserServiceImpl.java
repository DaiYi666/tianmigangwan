package com.tianmigangwan.service.impl;

import com.tianmigangwan.mapper.UserMapper;
import com.tianmigangwan.pojo.CommonResult;
import com.tianmigangwan.pojo.User;
import com.tianmigangwan.service.UserService;
import com.tianmigangwan.utils.ResponseCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public CommonResult authentication(User user, HttpSession session) {
        CommonResult result = new CommonResult<>();

        User record = userMapper.queryUserByPhoneNumber(user.getPhoneNumber());//调用mapper接口查询数据库
        user.passwordEncryption();

        if (user.getPassword().equals(record.getPassword())) {
            result.setResponseCode(ResponseCode.SUCCESS);
            result.setMessage("password authentication passed");
            session.setAttribute("user", record);
        } else {
            result.setResponseCode(ResponseCode.INVALID_PASSWORD);
            result.setMessage("wrong phone number or password");
        }
        return result;
    }

}
