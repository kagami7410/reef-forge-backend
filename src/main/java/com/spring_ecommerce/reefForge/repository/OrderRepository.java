package com.spring_ecommerce.reefForge.repository;

import com.spring_ecommerce.reefForge.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
