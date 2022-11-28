package com.brandlogs.vendorservice.component;

import com.brandlogs.vendorservice.dto.VendorRequest;
import com.brandlogs.vendorservice.entity.Vendor;
import org.springframework.stereotype.Component;

@Component
public class DtoToEntityMapper {
    public Vendor mapToVendor(VendorRequest vendor) {
        return Vendor.builder()
                .name(vendor.name())
                .email(vendor.email())
                .location(vendor.location())
                .contact(vendor.contact())
                .build();
    }
}
