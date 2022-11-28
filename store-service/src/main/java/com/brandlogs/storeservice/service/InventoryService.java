package com.brandlogs.storeservice.service;

import com.brandlogs.storeservice.component.DtoToEntityMapper;
import com.brandlogs.storeservice.component.EntityToDtoMapper;
import com.brandlogs.storeservice.component.HttpClient;
import com.brandlogs.storeservice.dto.*;
import com.brandlogs.storeservice.entity.Inventory;
import com.brandlogs.storeservice.entity.ReturnedItem;
import com.brandlogs.storeservice.enums.ReturnedItemSource;
import com.brandlogs.storeservice.exception.ModelNotFoundException;
import com.brandlogs.storeservice.exception.QuantityExceededException;
import com.brandlogs.storeservice.repository.InventoryRepository;
import com.brandlogs.storeservice.repository.ReturnedItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final EntityToDtoMapper entityToDtoMapper;

    private final DtoToEntityMapper dtoToEntityMapper;

    private final ReturnedItemRepository returnedItemRepository;
    private final HttpClient httpClient;

    public InventoryService(InventoryRepository inventoryRepository,
                            EntityToDtoMapper entityToDtoMapper,
                            DtoToEntityMapper dtoToEntityMapper,
                            ReturnedItemRepository returnedItemRepository, HttpClient httpClient) {
        this.inventoryRepository = inventoryRepository;
        this.entityToDtoMapper = entityToDtoMapper;
        this.dtoToEntityMapper = dtoToEntityMapper;
        this.returnedItemRepository = returnedItemRepository;
        this.httpClient = httpClient;
    }


    public Page<InventoryResponse> findAllInventory(int page, int size, String period) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (period.equals("lastWeek")) {
            //get first and last day of last week
            LocalDateTime lastMonth =LocalDateTime.now().minusWeeks(1);
            LocalDateTime start = lastMonth.with(LocalTime.MIN).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDateTime end = lastMonth.with(LocalTime.MAX).with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

            return inventoryRepository.findByReceivedOnBetween(start, end, pageRequest)
                    .map(entityToDtoMapper::mapToInventoryResponse);
        }
        return inventoryRepository.findAll(pageRequest)
                .map(entityToDtoMapper::mapToInventoryResponse);
    }


    public void addInventory(InventoryRequest inventoryRequest) {
        List<DeliveredItem> deliveredItems = inventoryRequest.deliveredItems();
        List<Inventory> inventoryList = deliveredItems.stream()
                .map(deliveredItem -> {


                    Variant variant = httpClient.getVariantById(deliveredItem.variantId());
                    if(variant != null){
                        Inventory inventory = dtoToEntityMapper.mapToInventory(deliveredItem);
                        inventory.setVariant(variant.id());
                        inventory.setSupplier(inventoryRequest.supplier());
                        //we set quantity manually here because we only want it to happen on create
                        return inventory;
                    }

                    throw new ModelNotFoundException("Provided item "+deliveredItem.variantId()+" is not on database");

                })
                .toList();
        inventoryRepository.saveAll(inventoryList);


    }

    @Transactional
    public void returnSpoiltItem(ReturnItemRequest returnItemRequest,long id) {
        inventoryRepository.findById(id)
                .filter(inventory -> returnItemRequest.quantity() <= inventory.getBalanceQuantity())
                .ifPresentOrElse(inventory -> {
                    ReturnedItem returnedItem = ReturnedItem.builder()
                            .inventory(inventory)
                            .quantity(returnItemRequest.quantity())
                            .source(ReturnedItemSource.STORE)
                            .reason(returnItemRequest.reason())
                            .build();
                    inventoryRepository.updateInventoryBalance(inventory.getId(), returnedItem.getQuantity());
                    returnedItemRepository.save(returnedItem);
                }, () -> {
                    throw new QuantityExceededException("Provided quantity is higher than store balance");
                });
    }
}
