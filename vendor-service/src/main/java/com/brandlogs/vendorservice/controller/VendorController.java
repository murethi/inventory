package com.brandlogs.vendorservice.controller;

import com.brandlogs.vendorservice.dto.VendorRequest;
import com.brandlogs.vendorservice.dto.VendorResponse;
import com.brandlogs.vendorservice.service.VendorService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class VendorController {
    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Page<VendorResponse> index(){
        return vendorService.findAllVendors();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody VendorRequest vendorRequest){
        vendorService.createVendor(vendorRequest);
    }

    @GetMapping("/view/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorResponse update(@PathVariable long id){
        return vendorService.viewVendor(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@RequestBody VendorRequest vendorRequest,@PathVariable long id){
        vendorService.updateVendor(vendorRequest,id);
    }

}
