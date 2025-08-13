package com.spring_ecommerce.reefForge.services;



import com.spring_ecommerce.reefForge.models.User;
import com.spring_ecommerce.reefForge.repository.UserRepository;
import com.spring_ecommerce.reefForge.securityModels.AuthenticationRequest;
import com.spring_ecommerce.reefForge.securityModels.AuthenticationResponse;
import com.spring_ecommerce.reefForge.securityModels.RegisterRequest;
import com.spring_ecommerce.reefForge.securityModels.Role;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    Logger logger = LogManager.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    EmailService emailService;




    public AuthenticationResponse register(RegisterRequest request) {
        String message;
        boolean emailAlreadyExists = userRepository.existsByEmail(request.getEmail());

        if(!emailAlreadyExists){
            User user = new com.spring_ecommerce.reefForge.models.User();
            user.setEmail(request.getEmail());
            user.setRoles(Collections.singleton(request.getRole()));
            user.setAddress(request.getAddress());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setFullName(request.getFullName());
            System.out.println("user added: " + user.getFullName());
            if(user.getRoles().contains(Role.ADMIN)){
                message = "Created Admin User";
            }
            else{
                message = "Created User";
            }
            if (request.getPassword() != null){
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            else{
                user.setPassword(passwordEncoder.encode("test"));
            }

            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            logger.info("User Registered");

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .message(message).build();

        }
        else {
            return AuthenticationResponse.builder()
                    .token(null)
                    .message("Email already in use").build();
        }
    }




    public AuthenticationResponse preRegister(RegisterRequest request) {
        String message;
        boolean emailAlreadyExists = userRepository.existsByEmail(request.getEmail());


        if(!emailAlreadyExists){
            User user = new com.spring_ecommerce.reefForge.models.User();
            user.setRoles(Collections.singleton(request.getRole()));
            user.setAddress(request.getAddress());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
            user.setVerified(false);
            user.setPhoneNumber(request.getPhoneNumber());
            user.setFullName(request.getFullName());
            System.out.println("user added: " + user.getFullName());
            if(user.getRoles().contains(Role.ADMIN)){
                message = "Created Admin User";

            }
            else{
                message = "Created User";
            }
            if (request.getPassword() != null){
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            else{
                user.setPassword(passwordEncoder.encode("test"));
            }


            System.out.println("trying to pre-register user: " + request.getEmail());

            //extra jwt claims for fullname
            final  Map<String, String> extraJwtClaim = new HashMap<>();
            extraJwtClaim.put("fullName", user.getFullName());
            extraJwtClaim.put("email",request.getEmail());


            if(request.getRole() == Role.ADMIN && request.getEmail() == "sujangurung10@gmail.com"){
                extraJwtClaim.put("isAdmin", "true");

            }
            else{
                extraJwtClaim.put("isAdmin", "false");

            }




            var jwtToken = jwtService.generateToken(extraJwtClaim, user);
            String verificationLink = "http://localhost:3000/verifyEmail?token=" + jwtToken;

            try{
                System.out.println("trying to sent email for verification");

               if(emailService.sendConfirmaionEmail(request.getEmail(), "Reef-Forge Email Verification", "<html><body>" +
                       "<h1>Click the verify button to verify your email!</h1>" +
                       "<a href=\"" + verificationLink + "\" " +
                       "style=\"display:inline-block;padding:10px 20px;background-color:#4CAF50;color:white;text-decoration:none;border-radius:5px;\">" +
                       "Verify Email</a>" +
                       "</body></html>")){
                   System.out.println("email sent for verification");
                   userRepository.save(user);
                   return AuthenticationResponse.builder()
                           .token(jwtToken)
                           .message(message).build();

               }
               else {
                   return AuthenticationResponse.builder()
                           .message("Email is invalid").build();
               }




        } catch (jakarta.mail.SendFailedException e) {

            logger.error("Invalid email address: " +  e);
                return AuthenticationResponse.builder()
                        .message("error while sending email").build();
            } catch (MessagingException e) {
            logger.error("Messaging error while sending email to " + e);
                return AuthenticationResponse.builder()
                        .message("error while sending email").build();
        } catch (Exception e) {
            logger.error("Unexpected error while sending email to " +  e);
                return AuthenticationResponse.builder()
                        .message("error while sending email").build();
        }

        }

        return null;
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        String message;
        String jwtToken;
        Map<String, String> extraClaims = new HashMap<>();
        logger.info("Initialing Authentication.......");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        logger.info("User's email: " + user.getEmail());
        logger.info("JWT token Generated");

        if(user.getRoles().contains(Role.ADMIN)){
            message = "Authenticated Admin";
            extraClaims.put("isAdmin", "true");

            jwtToken = jwtService.generateToken(extraClaims, user);

        }
        else{
            message = "Authenticated User";
             jwtToken = jwtService.generateToken(user);

        }

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .message(message)
                .build();
    }
}
