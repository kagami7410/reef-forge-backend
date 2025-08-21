package com.spring_ecommerce.reefForge.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="items")
@JsonIgnoreProperties({"basketItems"})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Or InheritanceType.SINGLE_TABLE
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    String title;

    @Column
    String size;

    @Column
    String description;
 
    @Column
    List<String> photoUrls;

    @Column
    String code;

    @Column
    Float price;

    @Column
    int stockQuantity;


//    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
//    @JsonIgnoreProperties({"item"})
//    private Quantity quantity;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"item"})
    private Set<BasketItem> basketItems = new HashSet<>();



}
