package com.tianmigangwan.mapper;

import com.tianmigangwan.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User queryUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    int addUser(@Param("user") User user);

    boolean isPhoneNumberBinding(@Param("phoneNumber") String phoneNumber);

    boolean isEmailBinding(@Param("email") String email);

}
