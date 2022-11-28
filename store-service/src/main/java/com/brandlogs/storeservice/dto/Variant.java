package com.brandlogs.storeservice.dto;

import lombok.Builder;

@Builder
public record Variant(long id, String name, String sku, String brand, boolean isDeleted){
}
