package com.spring_ecommerce.reefForge.services;

import com.spring_ecommerce.reefForge.models.User;
import com.spring_ecommerce.reefForge.repository.UserRepository;
import com.spring_ecommerce.reefForge.securityModels.Role;
import com.spring_ecommerce.reefForge.services.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.spring_ecommerce.reefForge.securityModels.Role.ADMIN;

@Service
public class UserRegistrationService {

    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;


    @Transactional
    public User saveUserFromToken(String token) {

        String email = jwtService.extractEmail(token);
        boolean isAdmin = jwtService.extractIsAdmin(token);
        String fullName = jwtService.extractFullName(token);
        String userId = jwtService.extractUserId(token);



        User user = userRepository.findByUserId(userId).orElseThrow();

        System.out.println("user to verify: " + user.getFullName()  );

        user.setVerified(true);
        return userRepository.save(user);
    }
}