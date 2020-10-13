package com.tianmigangwan.controller;

import com.tianmigangwan.mapper.UserMapper;
import com.tianmigangwan.pojo.CommonResult;
import com.tianmigangwan.pojo.User;
import com.tianmigangwan.service.UserService;
import com.tianmigangwan.utils.ResponseCode;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    @Resource
    private UserMapper userMapper;


    @PostMapping("/authentication")
    public CommonResult authentication(@RequestBody User user, HttpSession session) {
        return userService.authentication(user, session);
    }


    @GetMapping("/getUserInformation")
    public CommonResult getUserInformation(HttpSession session) {
        CommonResult result = new CommonResult();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            result.setResponseCode(ResponseCode.UNAUTHORIZED_ACCESS);
            result.setMessage("unauthorized access");
        } else {
            result.setResponseCode(ResponseCode.SUCCESS);
            result.setMessage("user information was obtained successfully");
            result.setResponseBody(user);
        }
        return result;
    }


    @GetMapping("/logOut")
    public CommonResult logOut(HttpSession session){
        CommonResult result = new CommonResult();
        session.removeAttribute("user");
        return result;
    }


    @PostMapping("/register")
    public CommonResult register(@RequestBody User user){
        System.out.println(user);

        CommonResult result = new CommonResult();
        result.setResponseCode(ResponseCode.SUCCESS);
        return result;
    }



    @GetMapping("/test")
    public void test(@RequestParam("email")String email){
//        System.out.println(userMapper.isPhoneNumberBinding(phoneNumber));
//        System.out.println(userMapper.hasSpecifiedRecord("user","email",email));
    }

}
