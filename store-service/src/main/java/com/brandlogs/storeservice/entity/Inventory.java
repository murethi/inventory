package com.brandlogs.storeservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "variants_id",nullable = false,updatable = false)
    private long variant;

    @Column(name = "vendors_id",nullable = false,updatable = false)
    private long supplier;

    @Column(name = "received_quantity",nullable = false,updatable = false)
    private int receivedQuantity;

    @Column(name = "balance_quantity",nullable = false)
    private int balanceQuantity;

    @Column(name = "unit_price",nullable = false,updatable = false)
    private long unitPrice;

    @Column(name="created_at",nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime receivedOn;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inventory", cascade = CascadeType.ALL)
    private List<Shelf> shelves;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inventory", cascade = CascadeType.ALL)
    private List<ReturnedItem> returnedItems;
}
