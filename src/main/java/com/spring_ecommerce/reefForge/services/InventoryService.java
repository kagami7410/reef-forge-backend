package com.spring_ecommerce.reefForge.services;


import org.springframework.beans.factory.annotation.Autowired;



public interface InventoryService {

    boolean checkQuantity(Long id, int quantityToCheck);
}
