package com.brandlogs.productservice.dto;

import lombok.*;

import java.util.List;

@Builder
public record ProductResponse(long id,
                              String name,
                              String category,
                              boolean isDeleted,
                              List<VariantResponse> variants) {
}
