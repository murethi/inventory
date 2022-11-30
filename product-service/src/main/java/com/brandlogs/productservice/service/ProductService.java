package com.brandlogs.productservice.service;

import com.brandlogs.productservice.component.DtoToEntityMapper;
import com.brandlogs.productservice.component.EntityToDtoMapper;
import com.brandlogs.productservice.dto.ProductRequest;
import com.brandlogs.productservice.dto.ProductResponse;
import com.brandlogs.productservice.entity.Product;
import com.brandlogs.productservice.exception.ModelNotFoundException;
import com.brandlogs.productservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final EntityToDtoMapper entityToDtoMapper;
    private final DtoToEntityMapper dtoToEntityMapper;

    public ProductService(ProductRepository productRepository, EntityToDtoMapper entityToDtoMapper, DtoToEntityMapper dtoToEntityMapper) {
        this.productRepository = productRepository;
        this.entityToDtoMapper = entityToDtoMapper;
        this.dtoToEntityMapper = dtoToEntityMapper;
    }

    public Page<ProductResponse> findAllProducts(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        return productRepository.findAll(pageRequest)
                .map(entityToDtoMapper::mapToProductResponse);
    }

    public ProductResponse viewProduct(long id){
        return productRepository.findById(id)
                .map(entityToDtoMapper::mapToProductResponse)
                .orElseThrow(()->new ModelNotFoundException("Product with id %s not found".formatted(id)));
    }


    /**
     * adds a new product database
     * @param productRequest an object of the product request dto
     */
    public void createProduct(ProductRequest productRequest) {
        Product product = dtoToEntityMapper.mapToProduct(productRequest);
        productRepository.save(product);
    }

    public void updateProduct(ProductRequest productRequest,long id) {
        productRepository.findById(id)
                .ifPresentOrElse(product -> {
                    product.setName(productRequest.name());
                    product.setCategory(productRequest.category());
                    productRepository.save(product);
                },()->{
                    throw new ModelNotFoundException("Variant with id "+id+" not found");
                });
    }

    public void deleteProduct(long id) {
        productRepository.findById(id)
                .ifPresentOrElse(product -> {
                    product.setDeleted(true);
                    productRepository.save(product);
                },()->{
                    throw new ModelNotFoundException("Variant with id "+id+" not found");
                });
    }
}
