package com.brandlogs.productservice.controller;

import com.brandlogs.productservice.dto.ProductRequest;
import com.brandlogs.productservice.dto.ProductResponse;
import com.brandlogs.productservice.dto.VariantRequest;
import com.brandlogs.productservice.service.VariantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/variants/")
public class VariantController {
    private final VariantService variantService;

    public VariantController(VariantService variantService) {
        this.variantService = variantService;
    }

    /**
     * adds variant to product
     * @param variantRequest VariantRequest
     * @param productId pk of the product
     */
    @GetMapping("/create/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody VariantRequest variantRequest,@PathVariable long productId){
        variantService.createVariant(variantRequest,productId);
    }

    /**
     * updates variant model
     * @param variantRequest variant being updated
     * @param id pk of the variant being updated
     */
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@RequestBody VariantRequest variantRequest, @PathVariable long id){
        variantService.updateVariant(variantRequest,id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id){
        variantService.deleteVariant(id);
    }
}
