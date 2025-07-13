package com.spring_ecommerce.reefForge.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

// 2.1  Wire the Storage client once
@Configuration
public class GcsConfig {
    @Bean
    Storage gcs() throws IOException {
        GoogleCredentials credentials = ServiceAccountCredentials
                .fromStream(new FileInputStream("credentials/omega-vigil-413814-158bf59a2903.json"));
        return StorageOptions.newBuilder()
                .setProjectId("omega-vigil-413814")
                .setCredentials(credentials)
                .build()
                .getService();
    }
}
