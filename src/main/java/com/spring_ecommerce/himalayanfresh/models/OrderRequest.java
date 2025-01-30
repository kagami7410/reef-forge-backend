package com.spring_ecommerce.himalayanfresh.models;

import com.spring_ecommerce.himalayanfresh.securityModels.RegisterRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
public class OrderRequest {
    private RegisterRequest registerRequest;
    private Set<OrderedItem> orderedItems;
}
