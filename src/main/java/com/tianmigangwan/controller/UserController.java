package com.tianmigangwan.controller;

import com.tianmigangwan.pojo.CommonResult;
import com.tianmigangwan.pojo.User;
import com.tianmigangwan.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/authentication")
    public CommonResult authentication(@RequestBody User user, HttpSession session) {
        return userService.authentication(user,session);
    }

}
