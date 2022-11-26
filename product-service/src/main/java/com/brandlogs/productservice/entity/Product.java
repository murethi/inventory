package com.brandlogs.productservice.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String category;

    @Column(name = "created_at",nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate createdAt;

    @Column(name = "updated_at",insertable = false)
    @UpdateTimestamp
    private LocalDate updatedAt;

    private boolean isDeleted;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "products_id",referencedColumnName = "id")
    private List<Variant> variants;

}
