package com.spring_ecommerce.reefForge.component;

import com.spring_ecommerce.reefForge.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UnverifiedUserCleanupJob {

    private final UserRepository userRepository;

    public UnverifiedUserCleanupJob(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Runs every day at midnight
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteUnverifiedUsers() {
        userRepository.deleteByVerifiedFalse();
        System.out.println("Deleted unverified users");
    }
}