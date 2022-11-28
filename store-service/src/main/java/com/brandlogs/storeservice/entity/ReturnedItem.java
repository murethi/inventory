package com.brandlogs.storeservice.entity;

import com.brandlogs.storeservice.enums.ReturnedItemSource;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "returned_items")
public class ReturnedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //if returned item was already on the supermarket floor
    private ReturnedItemSource source;

    private String reason;

    private int quantity;


    @JsonManagedReference
    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @Column(name="created_at",nullable = false,updatable = false)
    @CreationTimestamp
    private LocalDateTime returnDate;
}
