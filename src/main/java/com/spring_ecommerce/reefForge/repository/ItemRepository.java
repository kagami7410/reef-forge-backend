package com.spring_ecommerce.reefForge.repository;

import com.spring_ecommerce.reefForge.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
