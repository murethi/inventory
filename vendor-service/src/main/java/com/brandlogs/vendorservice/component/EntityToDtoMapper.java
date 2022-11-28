package com.brandlogs.vendorservice.component;

import com.brandlogs.vendorservice.dto.VendorResponse;
import com.brandlogs.vendorservice.entity.Vendor;
import org.springframework.stereotype.Component;

@Component
public class EntityToDtoMapper {

    public VendorResponse mapToVendorResponse(Vendor vendor) {
        return VendorResponse.builder()
                .id(vendor.getId())
                .name(vendor.getName())
                .email(vendor.getEmail())
                .location(vendor.getLocation())
                .contact(vendor.getContact())
                .build();
    }
}
