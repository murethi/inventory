package com.brandlogs.storeservice.service;


import com.brandlogs.storeservice.dto.ReturnItemRequest;
import com.brandlogs.storeservice.entity.ReturnedItem;
import com.brandlogs.storeservice.entity.Shelf;
import com.brandlogs.storeservice.enums.ReturnedItemSource;
import com.brandlogs.storeservice.exception.QuantityExceededException;
import com.brandlogs.storeservice.repository.InventoryRepository;
import com.brandlogs.storeservice.repository.ReturnedItemRepository;
import com.brandlogs.storeservice.repository.ShelfRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ShelfService {
    private final ShelfRepository shelfRepository;
    private final InventoryRepository inventoryRepository;
    private final ReturnedItemRepository returnedItemRepository;

    public ShelfService(ShelfRepository shelfRepository,
                        InventoryRepository inventoryRepository,
                        ReturnedItemRepository returnedItemRepository) {
        this.shelfRepository = shelfRepository;
        this.inventoryRepository = inventoryRepository;
        this.returnedItemRepository = returnedItemRepository;
    }

    public Page<Shelf> findAllShelves(int page, int size,String period){
        PageRequest pageRequest = PageRequest.of(page,size);
        if(period.equals("today")){
            LocalDateTime today =LocalDateTime.now();
            LocalDateTime start = today.with(LocalTime.MIN);
            LocalDateTime end = today.with(LocalTime.MAX);

            return shelfRepository.findByReceivedOnBetween(start,end,pageRequest);
        }
        return shelfRepository.findAll(pageRequest);
    }


    @Transactional
    public void addToShelf(long inventoryId, int quantity){
        inventoryRepository.findById(inventoryId)
                .filter(inventory -> quantity<=inventory.getBalanceQuantity())
                .ifPresentOrElse(inventory -> {
                    Shelf shelf = Shelf.builder()
                            .inventory(inventory)
                            .receivedQuantity(quantity)
                            .balanceQuantity(quantity)
                            .build();
                    inventoryRepository.updateInventoryBalance(inventoryId,quantity);
                    shelfRepository.save(shelf);
                },()->{ throw new QuantityExceededException("Batch balance does not satisfy requested volume");});
    }

    @Transactional
    public void returnSpoiltItem(ReturnItemRequest returnItemRequest,long id) {
        inventoryRepository.findById(id)
                .filter(inventory -> returnItemRequest.quantity() <= inventory.getBalanceQuantity())
                .ifPresentOrElse(inventory -> {
                    ReturnedItem returnedItem = ReturnedItem.builder()
                            .inventory(inventory)
                            .quantity(returnItemRequest.quantity())
                            .source(ReturnedItemSource.SHELF)
                            .reason(returnItemRequest.reason())
                            .build();
                    shelfRepository.updateShelfBalance(inventory.getId(), returnedItem.getQuantity());
                    returnedItemRepository.save(returnedItem);
                }, () -> {
                    throw new QuantityExceededException("Provided quantity is higher than that on the shelf");
                });
    }
}
