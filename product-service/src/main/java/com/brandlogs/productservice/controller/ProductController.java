package com.brandlogs.productservice.controller;

import com.brandlogs.productservice.dto.ProductRequest;
import com.brandlogs.productservice.dto.ProductResponse;
import com.brandlogs.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductResponse> index(@RequestParam(value = "page",defaultValue = "0", required = false) int page, @RequestParam(value = "size",defaultValue = "20",required = false) int size){
        return productService.findAllProducts(page,size);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }


    @GetMapping("/view/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse update(@PathVariable long id){
        return productService.viewProduct(id);
    }


    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void update(@RequestBody ProductRequest productRequest, @PathVariable long id){
        productService.updateProduct(productRequest,id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id){
        productService.deleteProduct(id);
    }
}
