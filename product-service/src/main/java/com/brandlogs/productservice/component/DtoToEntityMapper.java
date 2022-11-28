package com.brandlogs.productservice.component;

import com.brandlogs.productservice.dto.ProductRequest;
import com.brandlogs.productservice.dto.VariantRequest;
import com.brandlogs.productservice.entity.Product;
import com.brandlogs.productservice.entity.Variant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoToEntityMapper {
    public Variant mapToVariant(VariantRequest variantRequest){
        return Variant.builder()
                .sku(variantRequest.sku())
                .name(variantRequest.name())
                .brand(variantRequest.brand())
                .build();
    }

    public Product mapToProduct(ProductRequest productRequest){
        List<VariantRequest> variantRequests =productRequest.variants();
        return Product.builder()
                .category(productRequest.category())
                .name(productRequest.name())
                .variants(variantRequests.stream().map(this::mapToVariant)
                        .collect(Collectors.toList()))
                .build();
    }

}
