package com.spring_ecommerce.reefForge.services;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendConfirmaionEmail(String to, String subject, String text) throws MessagingException {
        // Create a MimeMessage
        MimeMessage message = mailSender.createMimeMessage();

        // Use MimeMessageHelper to manage multipart and HTML content
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Set the recipient, subject, and HTML content
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom("himalayancoffee@himalayanfresh.uk");
        helper.setText(text, true); // true enables HTML rendering


        mailSender.send(message);
        System.out.println("sucessfully sent email");
    }
}