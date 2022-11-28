package com.brandlogs.storeservice.repository;

import com.brandlogs.storeservice.entity.ReturnedItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ReturnedItemRepository extends JpaRepository<ReturnedItem,Long> {

    Page<ReturnedItem> findByReturnDateBetween(LocalDateTime start,LocalDateTime end, PageRequest pageRequest);
}
