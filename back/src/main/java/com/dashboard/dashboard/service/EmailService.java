package com.dashboard.dashboard.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendWelcomingEmail(String userName, String userEmail) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setSubject("Boas vindas ao Dashboard Financeiro!");
        message.setFrom(new InternetAddress("ferreiramartinsandreia@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, userEmail);
        String htmlTemplate = readFile(
                "dashboard-financeiro-projeto-final/src/main/java/com/dashboard/dashboard/template/WelcomeTemplateEmail.html");
        String htmlContent = htmlTemplate.replace("{{user_name}}", userName);

        message.setContent(htmlContent, "text/html; charset=utf-8");

        mailSender.send(message);
    }

    public void sendForgotPasswordEmail(String userEmail) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setSubject("Altere a sua senha");
        message.setFrom(new InternetAddress("ferreiramartinsandreia@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, userEmail);

        String htmlTemplate = readFile(
                "dashboard-financeiro-projeto-final/src/main/java/com/dashboard/dashboard/template/ResetPassowrdTemplate.html");

        String resetPasswordLink = "http://localhost:8282/reset-password?email=" + userEmail;

        String htmlContent = htmlTemplate
                .replace("{{user_email}}", userEmail)
                .replace("{{pass_reset_link}}", resetPasswordLink);

        message.setContent(htmlContent, "text/html; charset=utf-8");

        mailSender.send(message);
    }

    public String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        return Files.readString(path, StandardCharsets.UTF_8);
    }

}
