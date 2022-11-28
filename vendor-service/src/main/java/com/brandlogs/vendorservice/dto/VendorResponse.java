package com.brandlogs.vendorservice.dto;


import lombok.Builder;

@Builder
public record VendorResponse(long id, String name,String contact,String email,String location) {
}
