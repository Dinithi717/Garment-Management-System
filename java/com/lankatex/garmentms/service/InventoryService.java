package com.lankatex.garmentms.service;

import com.lankatex.garmentms.model.InventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService {

    InventoryItem createInventoryItem(String itemName, String itemCode, Integer quantity, Double unitPrice, String description);
    InventoryItem updateInventoryItem(Long id, String itemName, String itemCode, Integer quantity, Double unitPrice, String description);
    void deleteInventoryItem(Long id);
    InventoryItem findInventoryItemById(Long id);
    Page<InventoryItem> listAllInventoryItems(Pageable pageable);
    void updateStockQuantity(Long itemId, Integer changeQuantity);
}