package com.brandlogs.storeservice.controller;

import com.brandlogs.storeservice.dto.ReturnedItemResponse;
import com.brandlogs.storeservice.service.ReturnedItemService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/returned/")
public class ReturnedItemsController {
    private final ReturnedItemService returnedItemService;

    public ReturnedItemsController(ReturnedItemService returnedItemService) {
        this.returnedItemService = returnedItemService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Page<ReturnedItemResponse> index(
            @RequestParam(required = false,defaultValue = "") String filter,
            @RequestParam(value = "page",defaultValue = "0", required = false) int page,
            @RequestParam(value = "size",defaultValue = "20", required = false) int size){
        return returnedItemService.findAllReturnedItems(page,size,filter);
    }
}
