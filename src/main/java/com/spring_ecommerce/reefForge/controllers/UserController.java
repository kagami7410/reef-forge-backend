package com.spring_ecommerce.reefForge.controllers;

import com.spring_ecommerce.reefForge.models.*;
import com.spring_ecommerce.reefForge.repository.*;
import com.spring_ecommerce.reefForge.securityModels.AuthenticationRequest;
import com.spring_ecommerce.reefForge.securityModels.AuthenticationResponse;
import com.spring_ecommerce.reefForge.securityModels.RegisterRequest;
import com.spring_ecommerce.reefForge.services.AuthenticationService;
import com.spring_ecommerce.reefForge.services.OrderServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;


    @Autowired
    BasketItemRepository basketItemRepository;

//    @Autowired
//    BasketItem basketItem;

    @Autowired
    OrderRepository orderRepository;


    @GetMapping("/getAll")
    private List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @DeleteMapping("/deleteAll")
    private String deleteAllUsers(){
        userRepository.deleteAll();
        return "all users deleted";
    }


    @PostMapping("/register")
    private ResponseEntity<AuthenticationResponse> addUser(
            @RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) {
        logger.info("Autenticating...");
        Cookie cookie = new Cookie("admin", "true");
        cookie.setHttpOnly(true);
//        cookie.setSecure(true); // Set to true in production environments
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 1 week
        cookie.setDomain("localhost"); // Set to your domain if needed
        response.addCookie(cookie);

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }



//    @PostMapping("/submitOrder")
//    ResponseEntity<?> submitOrder(
//            @RequestBody Set<BasketItem> basketItems
//    ){
//
//        if(!basketItems.isEmpty()){
//            if(userRepository.existsByEmail(userRequest.getEmail())){
//                User user = userRepository.findByEmail(userRequest.getEmail()).orElse(null);
//                orderService.submitOrder(user, basketItems);
//            }
//            else {
//                authenticationService.register(userRequest);
//                orderService.submitOrder(userRepository.findByEmail(userRequest.getEmail()).orElse(null), basketItems);
//            }
//            return ResponseEntity.ok("Order Submitted!");
//        }
//        throw new RuntimeException("Basket not found or Error in database");
//
//
//    }

    @PostMapping("/submitOrder")
    ResponseEntity<?> submitOrder(
            @RequestBody HashMap<Long, Integer> orderedItemsList,
            @RequestParam String userEmail

    ){

        User user = userRepository.findByEmail(userEmail).orElse(null);
        logger.info("Setting Order for user: ", user.getEmail());
        Order order = new Order();
        order.setUser(user);
        orderRepository.save(order);

        if(!orderedItemsList.isEmpty()) {
            orderedItemsList.
                    forEach((orderedItemId, quantity) -> {
                        BasketItem basketItem = new BasketItem();
                        logger.info("processing ordered item for item: ", itemRepository.findById(orderedItemId).orElse(null).getTitle());
                        basketItem.setItem(itemRepository.findById(orderedItemId).orElse(null));
                        basketItem.setQuantity(quantity);
                        basketItem.setOrder(order);
                        basketItemRepository.save(basketItem);
                    });
            logger.info(" all ordered items processed!");
            return ResponseEntity.ok("Order is submitted!");

        }


        throw new RuntimeException("Basket not found or Error in database");


    }





}
