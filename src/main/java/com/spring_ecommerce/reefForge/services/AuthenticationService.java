package com.spring_ecommerce.reefForge.services;



import com.spring_ecommerce.reefForge.models.User;
import com.spring_ecommerce.reefForge.repository.UserRepository;
import com.spring_ecommerce.reefForge.securityModels.AuthenticationRequest;
import com.spring_ecommerce.reefForge.securityModels.AuthenticationResponse;
import com.spring_ecommerce.reefForge.securityModels.RegisterRequest;
import com.spring_ecommerce.reefForge.securityModels.Role;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    Logger logger = LogManager.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;




    public AuthenticationResponse register(RegisterRequest request) {
        String message;
        boolean emailAlreadyExists = userRepository.existsByEmail(request.getEmail());

        if(!emailAlreadyExists){
            User user = new com.spring_ecommerce.reefForge.models.User();
            user.setEmail(request.getEmail());
            user.setRoles(Collections.singleton(request.getRole()));
            user.setAddress(request.getAddress());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setPostCode(request.getPostCode());
            user.setTown(request.getTown());
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

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        String message;
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
        var jwtToken = jwtService.generateToken(user);
        logger.info("JWT token Generated");

        if(user.getRoles().contains(Role.ADMIN)){
            message = "Authenticated Admin";
        }
        else{
            message = "Authenticated User";
        }

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .message(message)
                .build();
    }
}
