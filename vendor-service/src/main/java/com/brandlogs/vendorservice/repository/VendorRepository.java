package com.brandlogs.vendorservice.repository;

import com.brandlogs.vendorservice.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor,Long> {
}
