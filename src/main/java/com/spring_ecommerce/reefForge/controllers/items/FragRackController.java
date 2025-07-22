package com.spring_ecommerce.reefForge.controllers.items;

import com.spring_ecommerce.reefForge.models.CoffeeItem;
import com.spring_ecommerce.reefForge.models.FragRackItem;
import com.spring_ecommerce.reefForge.models.Item;
import com.spring_ecommerce.reefForge.repository.FragRacksRepository;
import com.spring_ecommerce.reefForge.services.FetchItemServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fragRacks")
public class FragRackController {

    @Autowired
    FetchItemServiceImpl fetchItemService;
    Logger logger = LogManager.getLogger(FragRackController.class);
    @Autowired
    FragRacksRepository fragRacksRepository;


    @PostMapping("/add")
    public ResponseEntity<String> addItem (@RequestBody FragRackItem fragRackItem){
        System.out.println("-------------------------------- Initilialing item add---------------------------------------");
        boolean isItemAlreadyAdded = fragRacksRepository.findAll().stream()
                .anyMatch(item -> item.getTitle().equalsIgnoreCase(fragRackItem.getTitle()));
        if(!isItemAlreadyAdded){
            fragRacksRepository.save(fragRackItem);
            System.out.println("-------------------------------- item added ---------------------------------------");
            return ResponseEntity.ok(fragRackItem.getTitle() +" is added!");
        }
        else {
            System.out.println("-------------------------------- item  already in the database ---------------------------------------");
            return ResponseEntity.ok(fragRackItem.getTitle() +" is already in the database");
        }
    }


    @PutMapping("/update")
    public ResponseEntity<String> updateItem( @RequestBody FragRackItem fragRackItem) {
        System.out.println(fragRackItem.getTitle());

        FragRackItem itemOptional = fragRacksRepository.findById(fragRackItem.getId()).orElse(null);

        if (itemOptional.equals(null)) {
            return ResponseEntity.notFound().build();
        }

        itemOptional.setCode(fragRackItem.getCode());
        itemOptional.setPrice(fragRackItem.getPrice());
        System.out.println("Updating stock quantity to: "+ fragRackItem.getStockQuantity());
        itemOptional.setStockQuantity(fragRackItem.getStockQuantity());
        itemOptional.setPhotoUrls(fragRackItem.getPhotoUrls());
        itemOptional.setTitle(fragRackItem.getTitle());
        itemOptional.setDescription(fragRackItem.getDescription());

        fragRacksRepository.save(itemOptional);

        return ResponseEntity.ok(fragRackItem.getTitle() + " updated!");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FragRackItem>> getAllItems(){
        return ResponseEntity.ok(fragRacksRepository.findAll());
    }

    @GetMapping("/getById")
    public ResponseEntity<FragRackItem> getById(
            @RequestParam Long itemId
    ){
        return ResponseEntity.ok(fragRacksRepository.findById(itemId).orElseThrow());
    }


    @DeleteMapping("/deleteAll")
    private String deleteAllUsers(){
        fragRacksRepository.deleteAll();
        return "all users deleted";
    }

    @DeleteMapping("deleteById")
    public ResponseEntity<String> deleteByiD(
            @RequestParam Long itemId
    ){
        System.out.println("item id to delete: " + itemId);
        fragRacksRepository.deleteById(itemId);
        return ResponseEntity.ok("item deleted");
    }


    @GetMapping("/getByPage")
    public ResponseEntity<?> getByPage(
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ){
        logger.info("Fetching items from database. Contents of size {}", pageSize);

        return ResponseEntity.ok(fetchItemService.getFragRackItemsByPage(pageNumber, pageSize));
    }
//    @GetMapping("/getByCoralType")
//    public ResponseEntity<List<FragRackItem>> getByCoralType(){
//        return ResponseEntity.ok(fragRacksRepository.findByCoralType("LPS"));
//    }



}
