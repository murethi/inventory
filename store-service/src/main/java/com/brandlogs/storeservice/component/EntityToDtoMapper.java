package com.brandlogs.storeservice.component;

import com.brandlogs.storeservice.dto.InventoryResponse;
import com.brandlogs.storeservice.dto.ReturnedItemResponse;
import com.brandlogs.storeservice.entity.Inventory;
import com.brandlogs.storeservice.entity.ReturnedItem;
import org.springframework.stereotype.Component;

@Component
public class EntityToDtoMapper {
    private final HttpClient httpClient;

    public EntityToDtoMapper(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public InventoryResponse mapToInventoryResponse(Inventory inventory) {
        //long variant, long supplier, int receivedQuantity, int balanceQuantity, long unitPrice, LocalDateTime receivedOn
        return InventoryResponse.builder()
                .id(inventory.getId())
                .supplier(httpClient.getVendorById(inventory.getSupplier()))
                .shelves(inventory.getShelves())
                .variant(httpClient.getVariantById(inventory.getVariant()))
                .receivedQuantity(inventory.getReceivedQuantity())
                .balanceQuantity(inventory.getBalanceQuantity())
                .unitPrice(inventory.getUnitPrice())
                .receivedOn(inventory.getReceivedOn())
        .build();
    }

    public ReturnedItemResponse mapToReturnedItemResponse(ReturnedItem returnedItem) {
        Inventory inventory = returnedItem.getInventory();
        return ReturnedItemResponse.builder()
                .source(returnedItem.getSource())
                .quantity(returnedItem.getQuantity())
                .reason(returnedItem.getReason())
                .variant(httpClient.getVariantById(inventory.getVariant()))
                .build();
    }
}
