package com.spring_ecommerce.reefForge.controllers;

import com.spring_ecommerce.reefForge.models.*;
import com.spring_ecommerce.reefForge.repository.*;
import com.spring_ecommerce.reefForge.securityModels.AuthenticationRequest;
import com.spring_ecommerce.reefForge.securityModels.AuthenticationResponse;
import com.spring_ecommerce.reefForge.securityModels.RegisterRequest;
import com.spring_ecommerce.reefForge.services.AuthenticationService;
import com.spring_ecommerce.reefForge.services.OrderServiceImpl;
import com.spring_ecommerce.reefForge.services.ResetPasswordService;
import com.spring_ecommerce.reefForge.services.UserRegistrationService;
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
    ResetPasswordService resetPasswordService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;


    @Autowired
    BasketItemRepository basketItemRepository;

    @Autowired
    UserRegistrationService userRegistrationService;

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
    private ResponseEntity addUser(
            @RequestParam String token
    ){

        System.out.println("trying to add user");
        userRegistrationService.saveUserFromToken(token);
        return ResponseEntity.ok("user registered");



    }



// old register controller


//    @PostMapping("/register")
//    private ResponseEntity addUser(
//            @RequestBody RegisterRequest request,
//            @RequestParam String token) {
//
//        System.out.println("trying to add user");
//        if (authenticationService.register(request).getMessage().contains("already")) {
//            return ResponseEntity.badRequest().body("email already in use");
//        } else {
//            return ResponseEntity.ok(authenticationService.register(request));
//
//        }
//    }



// email is sent to user with jwt token where token includes the details of the user and all they have to do is click verify button
    @PostMapping("/preRegister")
    private ResponseEntity verifyEmailAndRegister(
            @RequestBody RegisterRequest request){
        System.out.println("trying to verify and register user");
        if(userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body("email already in use");
        }
        else{
            if (authenticationService.preRegister(request).equals(AuthenticationResponse.builder()
                    .message("Email is invalid").build())) {
                return ResponseEntity.badRequest().body("Email is Invalid");
            }
            else{
                return ResponseEntity.ok(authenticationService.preRegister(request));
            }

        }
    }


    @GetMapping("/restPasswordInit")
    private ResponseEntity restPasswordInit(
            @RequestParam String email){
        System.out.println("trying to verify and register user");
        if(userRepository.existsByEmail(email)){

            resetPasswordService.sendEmailForReset(email);
            return ResponseEntity.ok("Password Reset");
        }
        else{
            return ResponseEntity.badRequest().body("Email is not registered with reef-forge");

        }
    }


    @PostMapping("/resetPassword")
    private ResponseEntity restPassword(
            @RequestParam String jwtToken,
            @RequestBody ResetPassword newPassword){
        System.out.println("trying to verify and register user");
        System.out.println("jwtToken: " + jwtToken);
            resetPasswordService.resetPassword(jwtToken, newPassword.newPassword());
            return ResponseEntity.ok("Password Reset");


    }




    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) {

        try{
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

            if(user.isVerified()){
                logger.info("Authenticating...");
                Cookie cookie = new Cookie("admin", "true");
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setMaxAge(60); // 1 week
                cookie.setDomain("localhost"); // Set to your domain if needed
                response.addCookie(cookie);

                return ResponseEntity.ok(authenticationService.authenticate(request));
            }
            else {
                return ResponseEntity.badRequest().body("User is not verified");
            }
        }
        catch (Exception e){
           return  ResponseEntity.badRequest().body(e.getMessage());
        }


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
