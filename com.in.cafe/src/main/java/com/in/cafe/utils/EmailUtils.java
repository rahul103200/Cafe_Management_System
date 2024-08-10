package com.in.cafe.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String Subject, String text, List<String> list){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("rahulgcptest105@gmail.com");
        message.setTo(to);
        message.setSubject(Subject);
        message.setText(text);
        if(list != null && !list.isEmpty())
              message.setCc(getCcArray(list));
        log.info(to);
        log.info("\n");
        log.info(list.toString());
        emailSender.send(message);
    }

    private String[] getCcArray(List<String> ccList){
        String[] cc = new String[ccList.size()];
        for(int i=0;i< ccList.size();i++){
            cc[i] = ccList.get(i);
        }
        return cc;
    }

    public void forgetMail(String to,String subject,String password) throws MessagingException{
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom("rahulgcptest105@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMsg = "<p><b>Your Login details for Cafe Management System</b><br><b>Email: </b> " + to + " <br><b>Password: </b> " + password + "<br><a href=\\\"http://localhost:4200/\\\">Click here to login</a></p>";
        message.setContent(htmlMsg,"text/html");
        emailSender.send(message);
    }

}
