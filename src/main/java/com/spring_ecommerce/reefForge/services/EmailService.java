package com.spring_ecommerce.reefForge.services;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    Logger logger = LogManager.getLogger(EmailService.class);


    public void sendConfirmaionEmail(String to, String subject, String text) throws MessagingException {
        System.out.println("initialising email seender");

        // Create a MimeMessage
        MimeMessage message = mailSender.createMimeMessage();

        // Use MimeMessageHelper to manage multipart and HTML content
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Set the recipient, subject, and HTML content
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom("himalayancoffee@himalayanfresh.uk");
        helper.setText(text, true); // true enables HTML rendering

        System.out.println("About to send msessage");
        mailSender.send(message);
        System.out.println("sucessfully sent email");
    }
}