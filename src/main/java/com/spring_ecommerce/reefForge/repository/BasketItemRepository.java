package com.spring_ecommerce.reefForge.repository;

import com.spring_ecommerce.reefForge.models.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {
}
