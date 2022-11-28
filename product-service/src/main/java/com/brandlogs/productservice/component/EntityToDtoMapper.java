package com.brandlogs.productservice.component;

import com.brandlogs.productservice.dto.ProductResponse;
import com.brandlogs.productservice.dto.VariantResponse;
import com.brandlogs.productservice.entity.Product;
import com.brandlogs.productservice.entity.Variant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityToDtoMapper {


    public VariantResponse mapToVariantResponse(Variant variant) {
        Product product = variant.getProduct();
        return VariantResponse
                .builder()
                .id(variant.getId())
                .name(String.format("%s %s",product.getName(),variant.getName()))
                .sku(variant.getSku())
                .brand(variant.getBrand())
                .isDeleted(variant.isDeleted())
                .build();
    }


    public ProductResponse mapToProductResponse(Product product) {
        List<Variant> variants = product.getVariants();

        //convert product variants to variant responses
        List<VariantResponse> variantResponses = variants.stream()
                .map(this::mapToVariantResponse
                ).collect(Collectors.toList());


        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .isDeleted(product.isDeleted())
                .variants(variantResponses)
                .build();
    }
}
