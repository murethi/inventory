package com.brandlogs.productservice.service;

import com.brandlogs.productservice.dto.ProductRequest;
import com.brandlogs.productservice.dto.ProductResponse;
import com.brandlogs.productservice.dto.VariantRequest;
import com.brandlogs.productservice.dto.VariantResponse;
import com.brandlogs.productservice.entity.Product;
import com.brandlogs.productservice.entity.Variant;
import com.brandlogs.productservice.exception.ModelNotFoundException;
import com.brandlogs.productservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private static ProductResponse mapToProductResponse(Product product) {
        List<Variant> variants =product.getVariants();
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .isDeleted(product.isDeleted())
                .variants(variants.stream()
                        .map(variant -> VariantResponse
                                .builder()
                                .id(variant.getId())
                                .name(variant.getName())
                                .sku(variant.getSku())
                                .brand(variant.getBrand())
                                .isDeleted(variant.isDeleted())
                                .build()
                        ).collect(Collectors.toList())
                )
                .build();
    }


    private static Product mapToProduct(ProductRequest productRequest){
        List<VariantRequest> variantRequests =productRequest.variants();
        return Product.builder()
                .category(productRequest.category())
                .name(productRequest.name())
                .variants(variantRequests.stream().map(variantRequest ->
                        Variant.builder()
                        .name(variantRequest.name())
                        .sku(variantRequest.sku())
                        .brand(variantRequest.brand())
                        .build()).collect(Collectors.toList()))
                .build();
    }




    public Page<ProductResponse> findAllProducts() {
        PageRequest pageRequest = PageRequest.of(0,50);
        return productRepository.findAll(pageRequest)
                .map(ProductService::mapToProductResponse);
    }

    public ProductResponse viewProduct(long id){
        return productRepository.findById(id)
                .map(ProductService::mapToProductResponse)
                .orElseThrow(()->new ModelNotFoundException("Product with id %s not found".formatted(id)));
    }


    /**
     * adds a new product database
     * @param productRequest an object of the product request dto
     */
    public void createProduct(ProductRequest productRequest) {
        Product product = mapToProduct(productRequest);
        System.out.println(product);
        productRepository.save(product);
    }

    public void updateProduct(ProductRequest productRequest,long id) {
        productRepository.findById(id)
                .ifPresent(product -> {
                    product.setName(productRequest.name());
                    product.setCategory(productRequest.category());
                    productRepository.save(product);
                });
    }

    public void deleteProduct(long id) {
        productRepository.findById(id)
                .ifPresent(product -> {
                    product.setDeleted(true);
                    productRepository.save(product);
                });
    }
}
