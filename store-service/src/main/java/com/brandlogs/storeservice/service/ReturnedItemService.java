package com.brandlogs.storeservice.service;

import com.brandlogs.storeservice.component.DtoToEntityMapper;
import com.brandlogs.storeservice.component.EntityToDtoMapper;
import com.brandlogs.storeservice.dto.ReturnedItemResponse;
import com.brandlogs.storeservice.repository.ReturnedItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

@Service
public class ReturnedItemService {
    private final ReturnedItemRepository returnedItemRepository;

    private final EntityToDtoMapper entityToDtoMapper;

    private final DtoToEntityMapper dtoToEntityMapper;

    public ReturnedItemService(ReturnedItemRepository returnedItemRepository, EntityToDtoMapper entityToDtoMapper, DtoToEntityMapper dtoToEntityMapper) {
        this.returnedItemRepository = returnedItemRepository;
        this.entityToDtoMapper = entityToDtoMapper;
        this.dtoToEntityMapper = dtoToEntityMapper;
    }

    public Page<ReturnedItemResponse> findAllReturnedItems(int page, int size, String filter) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (filter.equals("month")) {
            //get first and last day of last month
            LocalDateTime lastMonth =LocalDateTime.now().minusMonths(1);
            LocalDateTime start = lastMonth.with(LocalTime.MIN).with(TemporalAdjusters.firstDayOfMonth());
            LocalDateTime end = lastMonth.with(LocalTime.MAX).with(TemporalAdjusters.lastDayOfMonth());

            return returnedItemRepository.findByReturnDateBetween(start, end, pageRequest)
                    .map(entityToDtoMapper::mapToReturnedItemResponse);
        }
        return returnedItemRepository.findAll(pageRequest)
                .map(entityToDtoMapper::mapToReturnedItemResponse);
    }
}
