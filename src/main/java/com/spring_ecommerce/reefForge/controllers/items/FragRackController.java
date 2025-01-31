package com.spring_ecommerce.reefForge.controllers.items;

import com.spring_ecommerce.reefForge.models.FragRackItem;
import com.spring_ecommerce.reefForge.repository.FragRacksRepository;
import com.spring_ecommerce.reefForge.services.FetchItemServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        System.out.println("-------------------------------- Testing ---------------------------------------");
        boolean isItemAlreadyAdded = fragRacksRepository.findAll().stream()
                .anyMatch(item -> item.getTitle().equalsIgnoreCase(fragRackItem.getTitle()));
        if(!isItemAlreadyAdded){
            fragRacksRepository.save(fragRackItem);
            return ResponseEntity.ok(fragRackItem.getTitle() +" is added!");
        }
        else {
            return ResponseEntity.ok(fragRackItem.getTitle() +" is already in the database");
        }
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
