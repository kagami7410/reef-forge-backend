package com.spring_ecommerce.reefForge.services;

import com.spring_ecommerce.reefForge.models.Item;
import com.spring_ecommerce.reefForge.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements  InventoryService{

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public boolean checkQuantity(Long id, int quantityToCheck) {
        Item item = itemRepository.findById(id).orElse(null);

            if(item != null){
                if(item.getStockQuantity() > quantityToCheck){
                    return true;
                } else if (item.getStockQuantity() == quantityToCheck) {
                    return true;

                } else{
                    return false;
                }
            }
        return false;
    }
}
