package com.brandlogs.storeservice.controller;

import com.brandlogs.storeservice.dto.ReturnItemRequest;
import com.brandlogs.storeservice.entity.Shelf;
import com.brandlogs.storeservice.service.ShelfService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shelves/")
public class ShelfController {
    private final ShelfService shelfService;

    public ShelfController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Page<Shelf> index(
            @RequestParam(required = false,defaultValue = "") String filter,
            @RequestParam(value = "page",defaultValue = "0", required = false) int page,
            @RequestParam(value = "size",defaultValue = "20", required = false) int size){
        return shelfService.findAllShelves(page,size,filter);
    }


    @PostMapping("/add-to-shelf")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            @RequestParam(value = "quantity") int quantity,
            @RequestParam(value = "inventoryId",defaultValue = "20", required = false) long inventoryId){
        shelfService.addToShelf(inventoryId,quantity);
    }

    @PostMapping("/return-spoilt-item/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void returnSpoiltItem(@RequestBody ReturnItemRequest returnItemRequest,@PathVariable long id){
        shelfService.returnSpoiltItem(returnItemRequest,id);
    }


}
