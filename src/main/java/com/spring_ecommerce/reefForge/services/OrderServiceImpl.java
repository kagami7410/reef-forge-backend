package com.spring_ecommerce.reefForge.services;

import com.spring_ecommerce.reefForge.models.*;
import com.spring_ecommerce.reefForge.repository.ItemRepository;
import com.spring_ecommerce.reefForge.repository.OrderRepository;
import com.spring_ecommerce.reefForge.repository.UserRepository;
import com.spring_ecommerce.reefForge.securityModels.RegisterRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderPolicies {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;

    @Transactional
    public void submitOrder(RegisterRequest user, Set<OrderedItem> items) throws Exception{
        Order order = new Order();
        Set<BasketItem> basketItems = new HashSet<>();
        items.stream().forEach(
                        eachOrderedItem -> {
                            int basketItemAvailableQuantity = checkStock(eachOrderedItem);

                            if(basketItemAvailableQuantity > eachOrderedItem.getItemQuantity()){
                                Item item = itemRepository.findById(eachOrderedItem.getItemId()).orElseThrow(null);
                                item.setStockQuantity(item.getStockQuantity() - eachOrderedItem.getItemQuantity());
                                BasketItem basketItem = new BasketItem();
                                basketItem.setOrder(order);
                                basketItem.setItem(itemRepository.findById(eachOrderedItem.getItemId()).orElseThrow());
                                basketItem.setQuantity(eachOrderedItem.getItemQuantity());
                                basketItems.add(basketItem);
                                System.out.println("Item with id: " + eachOrderedItem.getItemId() + " has stock availablity!");
                            }
                            else {
                                Item item= itemRepository.findById(eachOrderedItem.getItemId()).orElse(null);
                                String message = "Only " + basketItemAvailableQuantity +  " available for : " + item.getTitle();
                                throw new RuntimeException(message);
                            }
                        }
                );
        order.setUser(userRepository.findByEmail(user.getEmail()).orElse(null));
        order.setBasketItems(basketItems);
        order.setDate(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Override
    public int checkStock(OrderedItem orderedItem) {
        int stockQunatity = 0;
        if(itemRepository.existsById(orderedItem.getItemId())){
            Item fetchedItem = itemRepository.findById(orderedItem.getItemId()).orElse(null);
            stockQunatity = fetchedItem.getStockQuantity();
        }
        return stockQunatity;
    }

    @Override
    public boolean checkUserExists(String email) {
        return userRepository.existsByEmail(email);
    }


}
