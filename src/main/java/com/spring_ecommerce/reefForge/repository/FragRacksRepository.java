package com.spring_ecommerce.reefForge.repository;

import com.spring_ecommerce.reefForge.models.FragRackItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FragRacksRepository extends JpaRepository<FragRackItem, Long> {
    FragRackItem getById(Long id);
}
