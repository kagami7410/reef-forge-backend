package com.spring_ecommerce.reefForge.securityModels;

import com.spring_ecommerce.reefForge.models.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String fullName;
    private String password;
    private String email;
    private Address address;
    private Role role;
    private String phoneNumber;

}
