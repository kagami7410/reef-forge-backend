package com.spring_ecommerce.reefForge.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

public record OrderDetailsDTO(String OrderId, Set<BasketItem> basketItems, LocalDateTime date) {
}
