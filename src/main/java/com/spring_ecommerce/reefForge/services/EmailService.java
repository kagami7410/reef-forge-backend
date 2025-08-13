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

//if email is sent returns true
    public boolean sendConfirmaionEmail(String to, String subject, String text) throws MessagingException {

        try{
            System.out.println("initialising email sender");

            // Create a MimeMessage
            MimeMessage message = mailSender.createMimeMessage();

            // Use MimeMessageHelper to manage multipart and HTML content
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Set the recipient, subject, and HTML content
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("noreply@reef-forge.uk");
            helper.setText(text, true); // true enables HTML rendering

            System.out.println("About to send message");
            mailSender.send(message);
            System.out.println("successfully sent email");

            return true;


    } catch (jakarta.mail.SendFailedException e) {

            logger.error("Invalid email address: " + to, e);
            return false;

        } catch (MessagingException e) {
        logger.error("Messaging error while sending email to " + to, e);
            return false;

        } catch (Exception e) {
        logger.error("Unexpected error while sending email to " + to, e);
            return false;

        }


    }
}