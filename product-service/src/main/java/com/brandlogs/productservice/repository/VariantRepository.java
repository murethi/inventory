package com.brandlogs.productservice.repository;

import com.brandlogs.productservice.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VariantRepository extends JpaRepository<Variant,Long> {
    List<Variant> findAllByIdIn(List<Long> ids);
}
