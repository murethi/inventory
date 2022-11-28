package com.brandlogs.vendorservice.dto;


import lombok.Builder;

@Builder
public record VendorRequest(String name, String contact, String email, String location) {
}
