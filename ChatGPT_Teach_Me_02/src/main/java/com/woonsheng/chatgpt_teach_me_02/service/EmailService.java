package com.woonsheng.chatgpt_teach_me_02.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendResetPasswordEmail(String toEmail, String token) {
        System.out.println("sendResetPasswordEmail is working... Token : " + token);
        Context context = new Context();
        String resetLink = "http://localhost:3000/reset-password?token=" + token;
        context.setVariable("resetLink", resetLink);
        String body = templateEngine.process("email/reset-password", context);
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Password Reset Request");
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}