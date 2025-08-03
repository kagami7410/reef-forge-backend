package com.spring_ecommerce.reefForge.models;

import com.spring_ecommerce.reefForge.securityModels.RegisterRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class OrderRequest {
    private RegisterRequest registerRequest;
    private Set<OrderedItem> orderedItems;
    private String orderId;
}
