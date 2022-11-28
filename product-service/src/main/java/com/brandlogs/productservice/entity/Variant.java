package com.brandlogs.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Variations of different products by brand, size or product code
 * e.g. product potato crisps -> deepys 50gms, tropical 100gms etc
 */
@Entity
@Data
@Builder
@Table(name = "variants")
@AllArgsConstructor
@NoArgsConstructor
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String brand;
    @Column(unique = true,updatable = false)
    private String sku;

    @Column(name = "created_at",nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @Column(name = "updated_at",insertable = false)
    @UpdateTimestamp
    private LocalDate updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "products_id")
    private Product product;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}

