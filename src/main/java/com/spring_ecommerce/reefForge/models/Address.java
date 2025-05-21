package com.spring_ecommerce.reefForge.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="addresses")

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String line1;

    @Column
    private String line2;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private String postCode;

    @JsonIgnoreProperties({"user"})
    @OneToOne(mappedBy = "address")
    private User user;




}
