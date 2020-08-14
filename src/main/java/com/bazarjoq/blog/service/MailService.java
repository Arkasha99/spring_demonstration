package com.bazarjoq.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void send(String emailTo, String subject, String text){
        SimpleMailMessage eMailMessage = new SimpleMailMessage();

        eMailMessage.setFrom(username);
        eMailMessage.setTo(emailTo);
        eMailMessage.setSubject(subject);
        eMailMessage.setText(text);

        mailSender.send(eMailMessage);
    }
}
