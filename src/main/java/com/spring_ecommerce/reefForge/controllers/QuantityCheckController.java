package com.spring_ecommerce.reefForge.controllers;

import com.spring_ecommerce.reefForge.services.InventoryService;
import com.spring_ecommerce.reefForge.services.InventoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/checkQuantity")
public class QuantityCheckController {

@Autowired
private InventoryServiceImpl inventoryService;

    @PostMapping("/verify")
    public ResponseEntity<?> verifyQuantity(
            @RequestParam Long productId,
            @RequestParam int productQuantityToVerify) {
        System.out.println("checking details for itemid:" + productId);
        boolean isAvailable = inventoryService.checkQuantity(productId, productQuantityToVerify);

        if (isAvailable) {
            return ResponseEntity.ok().body("available");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Insufficient quantity");
        }
    }
}
