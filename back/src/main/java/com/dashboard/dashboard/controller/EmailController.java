package com.dashboard.dashboard.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dashboard.dashboard.dto.NewUserEmailDTO;
import com.dashboard.dashboard.service.EmailService;
import com.dashboard.dashboard.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    UserService userService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping("/send-email")
    public ResponseEntity<String> sendEmailWithTemplate(@RequestBody NewUserEmailDTO emailRequest)
            throws MessagingException, IOException {

        emailService.sendWelcomingEmail(emailRequest.getName(), emailRequest.getEmail());

        return ResponseEntity.ok("Email sent successfully.");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassowrd(@RequestParam String email) throws IOException {
        return new ResponseEntity<>(userService.forgotPassword(email), HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email,
            @RequestBody Map<String, String> requestBody)
            throws IOException {

        String newPassword = requestBody.get("newPassword");

        return new ResponseEntity<>(userService.resetPassword(email, newPassword), HttpStatus.OK);
    }

}
