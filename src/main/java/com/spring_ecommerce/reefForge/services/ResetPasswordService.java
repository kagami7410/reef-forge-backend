package com.spring_ecommerce.reefForge.services;

import com.spring_ecommerce.reefForge.models.User;
import com.spring_ecommerce.reefForge.repository.UserRepository;
import com.spring_ecommerce.reefForge.securityModels.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class ResetPasswordService {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    public AuthenticationResponse resetPassword(String token, String newPassword) {

        String userEmail = jwtService.extractEmail(token);
        System.out.println("user email is: " + userEmail);
        boolean emailAlreadyExists = userRepository.existsByEmail(userEmail);


        if(emailAlreadyExists){
            User user = userRepository.findByEmail(userEmail).orElseThrow();


            if (newPassword != null){
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setVerified(true);
            }
            else{
                user.setPassword(passwordEncoder.encode("test"));
            }

            userRepository.save(user);
            System.out.println("trying to rest password for user: " + newPassword);

            //extra jwt claims for fullname
            final Map<String, String> extraJwtClaim = new HashMap<>();
            extraJwtClaim.put("fullName", user.getFullName());
            extraJwtClaim.put("email", userEmail);




            var jwtToken = jwtService.generateToken(extraJwtClaim, user);

            try{
                System.out.println("trying to sent email for reset confirmation");

                emailService.sendConfirmaionEmail(userEmail, "Reef-Forge Email Verification", "<html><body>" +
                        "<h1>Password reset</h1>" +
                        "</body></html>");

                System.out.println("email sent for verification");

            }
            catch (Exception e){
                e.getMessage();
            }



            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .message("password reset complete").build();

        }
        else {
            return AuthenticationResponse.builder()
                    .token(null)
                    .message("Email already in use").build();
        }
    }











    public AuthenticationResponse sendEmailForReset(String userEmail) {


        boolean emailAlreadyExists = userRepository.existsByEmail(userEmail);


        if(emailAlreadyExists){
            User user = userRepository.findByEmail(userEmail).orElseThrow();



            var jwtToken = jwtService.generateToken(user);

            String passwordResetLink = "http://localhost:3000/resetPassword?token=" + jwtToken;

            try{
                System.out.println("trying to sent email for password reset");

                emailService.sendConfirmaionEmail(userEmail, "Reef-Forge Password Reset", "<html><body>" +
                        "<h1>Password reset</h1>" +
                        "<a href=\"" + passwordResetLink + "\" " +
                        "style=\"display:inline-block;padding:10px 20px;background-color:#4CAF50;color:white;text-decoration:none;border-radius:5px;\">" +
                        "Reset Password</a>" +
                        "</body></html>");

                System.out.println("email sent for password reset");

            }
            catch (Exception e){
                e.getMessage();
            }



            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .message("password reset complete").build();

        }
        else {
            return AuthenticationResponse.builder()
                    .token(null)
                    .message("Email already in use").build();
        }
    }
}
