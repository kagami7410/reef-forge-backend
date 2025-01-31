package com.spring_ecommerce.reefForge.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="basket_items")
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"item", "basketItems"})
    private Item item;


    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"item"})
    private Order order;

    @Column
    private Integer quantity;
}
