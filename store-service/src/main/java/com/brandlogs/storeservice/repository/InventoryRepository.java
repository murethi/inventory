package com.brandlogs.storeservice.repository;


import com.brandlogs.storeservice.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    @Modifying
    @Query("UPDATE Inventory SET balanceQuantity=balanceQuantity - :quantity WHERE balanceQuantity>:quantity AND id=:id")
    int updateInventoryBalance(long id, int quantity);

    Page<Inventory> findByReceivedOnBetween(LocalDateTime start,LocalDateTime endLocalDateTime,PageRequest pageRequest);
}
