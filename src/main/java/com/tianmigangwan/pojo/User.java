package com.tianmigangwan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String nickname;
    private String headPortrait;
    private String gender;
    private String phoneNumber;
    private String email;
    private String city;
    private int photoCount;
    private int videoCount;
    private String password;
    private Date createTime;
    private Date updateTime;
    private String isSendLoginPromptEmail;

    public void passwordEncryption(){
        this.password=DigestUtils.md5DigestAsHex(this.password.getBytes());
    }

    public void generateId(){
        this.id=UUID.randomUUID().toString();
    }
}
