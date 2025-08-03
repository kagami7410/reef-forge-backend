package com.spring_ecommerce.reefForge.controllers;


import com.spring_ecommerce.reefForge.models.*;
import com.spring_ecommerce.reefForge.repository.OrderRepository;
import com.spring_ecommerce.reefForge.services.AuthenticationService;
import com.spring_ecommerce.reefForge.services.EmailService;
import com.spring_ecommerce.reefForge.services.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("getAll")
    ResponseEntity<?> getAllOrders(){
        return ResponseEntity.ok(orderRepository.findAll());
    }


    @DeleteMapping("deleteAll")
    void deleteAllOrders(){
         orderRepository.deleteAll();
    }


    @PostMapping("submitOrder")
    ResponseEntity<String> submitOrder(
                @RequestBody OrderRequest orderedRequest){
        System.out.println("trying to submit order............");
        System.out.println("Order Request for: " +  orderedRequest.getRegisterRequest().getFullName());
        if(orderService.checkUserExists(orderedRequest.getRegisterRequest().getEmail())){
            try{
                orderService.submitOrder(orderedRequest);
                emailService.sendConfirmaionEmail(orderedRequest.getRegisterRequest().getEmail(), "Reef-Forge", "<html><body><h1>Thank you for your Order!</h1><p>This is an <b>Confirmation</b> email for your order: " + orderedRequest.getOrderId()+"from Himalayan Fresh</p></body></html> " );
                return  new ResponseEntity<>("Order Submitted!", HttpStatus.ACCEPTED);
            }
            catch (Exception e){
                System.out.println("exception occoured while submitting order " + e.getMessage());

                return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
            }

        }
        else{
            authenticationService.register(orderedRequest.getRegisterRequest());
            try{
                orderService.submitOrder(orderedRequest);
                emailService.sendConfirmaionEmail(orderedRequest.getRegisterRequest().getEmail(), "Reef-Forge", "<html><body><h1>Thank you for your Order!</h1><p>This is an <b>Confirmation</b> email for your order: #23234352254 from Himalayan Fresh</p></body></html> " );

                return  new ResponseEntity<>("Order Submitted!", HttpStatus.ACCEPTED);
            }
            catch (Exception e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }
    }




}

