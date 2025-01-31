package com.spring_ecommerce.reefForge.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderedItem {
    private Long itemId;
    private int itemQuantity;
}
