package com.tianmigangwan.utils;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@AllArgsConstructor
public class MailSender extends Thread{

    private JavaMailSender mailSender;

    private String sender;

    private String recipient;

    private String subject;

    private String content;

    @Override
    public void run() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        this.mailSender.send(mailMessage);
    }


    public void sendEmail(){
        this.start();
    }
}
