package com.brandlogs.storeservice.dto;

import com.brandlogs.storeservice.entity.Shelf;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record InventoryResponse (long id, List<Shelf> shelves, Variant variant, Vendor supplier, int receivedQuantity, int balanceQuantity, long unitPrice, LocalDateTime receivedOn){
}
