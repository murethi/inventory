package com.brandlogs.productservice.dto;

import lombok.Builder;

@Builder
public record VariantResponse (long id, String name,String sku,String brand,boolean isDeleted){
}
