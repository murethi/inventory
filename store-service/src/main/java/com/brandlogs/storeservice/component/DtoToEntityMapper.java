package com.brandlogs.storeservice.component;

import com.brandlogs.storeservice.dto.DeliveredItem;
import com.brandlogs.storeservice.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class DtoToEntityMapper {

    public Inventory mapToInventory(DeliveredItem deliveredItem){
        return Inventory.builder()
                .variant(deliveredItem.variantId())
                .unitPrice(deliveredItem.unitPrice())
                .receivedQuantity(deliveredItem.receivedQuantity())
                .balanceQuantity(deliveredItem.receivedQuantity())
                .build();
    }
}
