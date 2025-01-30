package com.spring_ecommerce.himalayanfresh.controllers.items;


import com.spring_ecommerce.himalayanfresh.models.CoffeeItem;
import com.spring_ecommerce.himalayanfresh.models.Item;
import com.spring_ecommerce.himalayanfresh.models.Type;
import com.spring_ecommerce.himalayanfresh.repository.ItemRepository;
import com.spring_ecommerce.himalayanfresh.services.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    EmailService emailService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Item>> getAllItems(){
        return ResponseEntity.ok(itemRepository.findAll());
    }


    @DeleteMapping("/deleteAll")
    private String deleteAllUsers(){
        itemRepository.deleteAll();
        return "all users deleted";
    }


    @PostMapping("/add")
    public ResponseEntity<String> addItem (@RequestBody Item newItem){
        boolean isItemAlreadyAdded = itemRepository.findAll().stream()
                        .anyMatch(item -> item.getTitle().equalsIgnoreCase(newItem.getTitle()));
        if(!isItemAlreadyAdded){
            itemRepository.save(newItem);
            return ResponseEntity.ok(newItem.getTitle() +" is added!");
        }
        else {
            return ResponseEntity.ok(newItem.getTitle() +" is already in the database");
        }
    }



    @GetMapping("/send-email")
    public String sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text) throws MessagingException {
        System.out.println(to + subject + text);

        emailService.sendConfirmaionEmail(to, subject, text);
        return "Email sent successfully!";
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateItem( @RequestBody CoffeeItem item) {
        System.out.println(item.getTitle());

        Optional<Item> itemOptional = itemRepository.findById(item.getId());

        if (itemOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Item item1 = itemOptional.get();
        item1.setCode(item.getCode());
        item1.setPrice(item.getPrice());
        item1.setStockQuantity(item.getStockQuantity());
        item1.setTitle(item.getTitle());
        item1.setDescription(item.getDescription());
        itemRepository.save(item1);

        return ResponseEntity.ok(item.getTitle() + " updated!");
    }
}
