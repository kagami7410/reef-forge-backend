package com.spring_ecommerce.himalayanfresh.repository;

import com.spring_ecommerce.himalayanfresh.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
