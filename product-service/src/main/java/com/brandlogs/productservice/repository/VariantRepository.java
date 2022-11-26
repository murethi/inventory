package com.brandlogs.productservice.repository;

import com.brandlogs.productservice.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantRepository extends JpaRepository<Variant,Long> {
}
