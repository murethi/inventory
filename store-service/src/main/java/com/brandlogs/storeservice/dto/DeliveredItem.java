package com.brandlogs.storeservice.dto;

/**
 * holds the list of items that have been submitted in the inventory request
 * @param variantId
 * @param receivedQuantity
 * @param unitPrice
 */
public record DeliveredItem(long variantId, int receivedQuantity, long unitPrice){
}
