package com.spring_ecommerce.reefForge.securityModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String password;
    private String email;
    private String address;
    private Role role;
    private String postCode;
    private String town;
    private String phoneNumber;

}
