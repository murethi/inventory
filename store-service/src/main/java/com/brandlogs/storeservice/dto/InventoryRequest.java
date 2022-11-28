package com.brandlogs.storeservice.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record InventoryRequest (long supplier, List<DeliveredItem> deliveredItems){
}
