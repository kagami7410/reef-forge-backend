package com.spring_ecommerce.himalayanfresh.repository;

import com.spring_ecommerce.himalayanfresh.models.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {
}
