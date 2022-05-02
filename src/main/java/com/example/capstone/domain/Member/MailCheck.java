package com.example.capstone.domain.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MailCheck {
    @Autowired
    private JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "YOUR_EMAIL_ADDRESS";
    private String address;
    private String message;
    private int autoInt;
    Random ran = new Random();

    public void mailSend() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(this.address);
        message.setFrom(MailCheck.FROM_ADDRESS);
        message.setSubject("인증번호입니다");
        autoInt = ran.nextInt(100000);
        this.message = Integer.toString(autoInt);
        message.setText(this.message);
        mailSender.send(message);
    }

    public MailCheck() {
    }

    public MailCheck(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAutoInt() {
        return autoInt;
    }

    public void setAutoInt(int autoInt) {
        this.autoInt = autoInt;
    }
}
