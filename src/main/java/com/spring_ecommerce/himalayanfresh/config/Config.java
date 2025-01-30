package com.spring_ecommerce.himalayanfresh.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_ecommerce.himalayanfresh.models.DatabaseCredentials;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

@Configuration
public class Config {

//    @Bean
//    public DatabaseCredentials databaseCredentials() throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        DatabaseCredentials databaseCredentialsObj = objectMapper.readValue(new File("src/main/resources/secrets/databasesecret.json"), DatabaseCredentials.class);
//        // Example: Load properties from a file or manipulate the environment here
//        // This can be dynamic loading of credentials or any other logic.
//        System.setProperty("spring.datasource.url", "jdbc:mysql://localhost:3306/himalayanfresh_coffee");
//        System.setProperty("spring.datasource.username", "root");
//        System.setProperty("spring.datasource.password", "password");
//        System.setProperty("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
//
//
//        // You can also manipulate the Spring Environment here if needed
//        System.out.println("Database Configuration Loaded: " + "jdbc:mysql://localhost/himalayanfresh_coffee");
//        return databaseCredentialsObj;
//    }

    @Bean
//    @Primary
    public DataSource dataSource() throws IOException {
        return DataSourceBuilder
                .create()
                .username(databaseCredentials().getUsername())
                .password(databaseCredentials().getPassword())
                .url(databaseCredentials().getUrl())
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }

    @Bean
    public DatabaseCredentials databaseCredentials() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        DatabaseCredentials databaseCredentialsObj = objectMapper.readValue(new File("src/main/resources/secrets/databasesecret.json"), DatabaseCredentials.class);
        System.out.println("------------------------------------------ Inititalised Creds Reader -------------------------------------------------");
        return databaseCredentialsObj;
    }
}
