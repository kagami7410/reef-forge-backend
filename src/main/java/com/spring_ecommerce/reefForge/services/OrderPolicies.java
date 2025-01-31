package com.spring_ecommerce.reefForge.services;

import com.spring_ecommerce.reefForge.models.OrderedItem;

public interface OrderPolicies {
    int checkStock(OrderedItem orderedItem);
    boolean checkUserExists(String email);



}
