package com.brandlogs.storeservice.dto;


import lombok.Builder;

@Builder
public record Vendor(long id, String name, String contact, String email, String location) {
}
