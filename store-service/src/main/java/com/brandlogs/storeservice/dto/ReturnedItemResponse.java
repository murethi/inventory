package com.brandlogs.storeservice.dto;

import com.brandlogs.storeservice.enums.ReturnedItemSource;
import lombok.Builder;

@Builder
public record ReturnedItemResponse (ReturnedItemSource source, String reason, int quantity, Variant variant){
}
