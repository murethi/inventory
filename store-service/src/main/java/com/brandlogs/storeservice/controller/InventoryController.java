package com.brandlogs.storeservice.controller;

import com.brandlogs.storeservice.dto.InventoryRequest;
import com.brandlogs.storeservice.dto.InventoryResponse;
import com.brandlogs.storeservice.dto.ReturnItemRequest;
import com.brandlogs.storeservice.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory/")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Page<InventoryResponse> index(
            @RequestParam(required = false,defaultValue = "") String filter,
            @RequestParam(value = "page",defaultValue = "0", required = false) int page,
            @RequestParam(value = "size",defaultValue = "20", required = false) int size){
        return inventoryService.findAllInventory(page,size,filter);
    }


    @PostMapping("/create/")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody InventoryRequest inventoryRequest){
        inventoryService.addInventory(inventoryRequest);
    }

    @PostMapping("/return-spoilt-item/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void returnSpoiltItem(@RequestBody ReturnItemRequest returnItemRequest,@PathVariable long id){
        inventoryService.returnSpoiltItem(returnItemRequest,id);
    }

}
