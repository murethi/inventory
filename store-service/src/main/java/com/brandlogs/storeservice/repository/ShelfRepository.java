package com.brandlogs.storeservice.repository;

import com.brandlogs.storeservice.entity.Shelf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ShelfRepository extends JpaRepository<Shelf,Long> {
    @Modifying
    @Query("UPDATE Shelf SET balanceQuantity=balanceQuantity - :quantity WHERE balanceQuantity>:quantity AND id=:id")
    int updateShelfBalance(long id, int quantity);

    Page<Shelf> findByReceivedOnBetween(LocalDateTime start, LocalDateTime end, PageRequest pageRequest);
}
