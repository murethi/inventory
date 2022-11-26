package com.brandlogs.productservice.dto;

import java.util.List;

public record ProductRequest(String name, String category, List<VariantRequest> variants) {
}
