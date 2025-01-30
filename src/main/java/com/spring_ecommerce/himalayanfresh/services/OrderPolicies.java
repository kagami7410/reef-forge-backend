package com.spring_ecommerce.himalayanfresh.services;

import com.spring_ecommerce.himalayanfresh.models.BasketItem;
import com.spring_ecommerce.himalayanfresh.models.OrderedItem;
import com.spring_ecommerce.himalayanfresh.models.User;

public interface OrderPolicies {
    int checkStock(OrderedItem orderedItem);
    boolean checkUserExists(String email);



}
