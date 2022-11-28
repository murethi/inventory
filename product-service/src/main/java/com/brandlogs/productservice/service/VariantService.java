package com.brandlogs.productservice.service;

import com.brandlogs.productservice.component.DtoToEntityMapper;
import com.brandlogs.productservice.component.EntityToDtoMapper;
import com.brandlogs.productservice.dto.VariantRequest;
import com.brandlogs.productservice.dto.VariantResponse;
import com.brandlogs.productservice.entity.Variant;
import com.brandlogs.productservice.exception.ModelNotFoundException;
import com.brandlogs.productservice.repository.ProductRepository;
import com.brandlogs.productservice.repository.VariantRepository;
import org.springframework.stereotype.Service;

@Service
public class VariantService {
    private final VariantRepository variantRepository;
    private final ProductRepository productRepository;

    private final EntityToDtoMapper entityToDtoMapper;
    private final DtoToEntityMapper dtoToEntityMapper;

    public VariantService(VariantRepository variantRepository, ProductRepository productRepository, EntityToDtoMapper entityToDtoMapper, DtoToEntityMapper dtoToEntityMapper) {
        this.variantRepository = variantRepository;
        this.productRepository = productRepository;
        this.entityToDtoMapper = entityToDtoMapper;
        this.dtoToEntityMapper = dtoToEntityMapper;
    }

    public void createVariant(VariantRequest variantRequest,long productId){
        productRepository.findById(productId)
                .ifPresent(product -> {
                    Variant variant = dtoToEntityMapper.mapToVariant(variantRequest);
                    variant.setProduct(product);
                    variantRepository.save(variant);
                });
    }

    public VariantResponse viewVariant(long id) {
        return variantRepository.findById(id)
                .map(entityToDtoMapper::mapToVariantResponse)
                .orElseThrow(()->new ModelNotFoundException("Product variant with id "+id+" not found"));
    }

    public void updateVariant(VariantRequest variantRequest,long id){
        variantRepository.findById(id)
                .ifPresent(variant -> {
                    variant.setName(variantRequest.name());
                    variant.setBrand(variantRequest.brand());
                    variantRepository.save(variant);
                });
    }

    public void deleteVariant(long id) {
        variantRepository.findById(id)
                .ifPresent(variant -> {
                    variant.setDeleted(true);
                    variantRepository.save(variant);
                });
    }






}
