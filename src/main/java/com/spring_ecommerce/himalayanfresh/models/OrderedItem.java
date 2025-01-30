package com.spring_ecommerce.himalayanfresh.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderedItem {
    private Long itemId;
    private int itemQuantity;
}
