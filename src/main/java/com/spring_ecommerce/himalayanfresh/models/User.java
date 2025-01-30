package com.spring_ecommerce.himalayanfresh.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring_ecommerce.himalayanfresh.securityModels.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;
    @Column
    private String password;

    @Column
    private Set<Role> roles;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user"})
    private Set<Order> orders = new HashSet<>();

    @Column
    private String address;

    @Column
    private String postCode;

    @Column
    private String town;

    @Column
    private String phoneNumber;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    public void setAuthorities() {

    }




    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
