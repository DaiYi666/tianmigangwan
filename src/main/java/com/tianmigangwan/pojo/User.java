package com.tianmigangwan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.DigestUtils;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String nickname;
    private String gender;
    private String phoneNumber;
    private String email;
    private String password;
    private Date createTime;
    private Date updateTime;

    public void passwordEncryption(){
        this.password=DigestUtils.md5DigestAsHex(this.password.getBytes());
    }
}
