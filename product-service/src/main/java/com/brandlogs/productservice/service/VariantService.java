package com.brandlogs.productservice.service;

import com.brandlogs.productservice.dto.VariantRequest;
import com.brandlogs.productservice.entity.Variant;
import com.brandlogs.productservice.repository.ProductRepository;
import com.brandlogs.productservice.repository.VariantRepository;
import org.springframework.stereotype.Service;

@Service
public class VariantService {
    private final VariantRepository variantRepository;
    private final ProductRepository productRepository;

    public VariantService(VariantRepository variantRepository, ProductRepository productRepository) {
        this.variantRepository = variantRepository;
        this.productRepository = productRepository;
    }

    public void createVariant(VariantRequest variantRequest,long productId){
        productRepository.findById(productId)
                .ifPresent(product -> {
                    Variant variant = mapToVariant(variantRequest);
                    variant.setProduct(product);
                    variantRepository.save(variant);
                });
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

    public static Variant mapToVariant(VariantRequest variantRequest){
        return Variant.builder()
                .sku(variantRequest.sku())
                .name(variantRequest.name())
                .brand(variantRequest.brand())
                .build();
    }


}
