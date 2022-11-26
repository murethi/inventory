package com.brandlogs.productservice.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

