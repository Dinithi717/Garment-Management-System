package com.lankatex.garmentms.service.impl;

import com.lankatex.garmentms.model.InventoryItem;
import com.lankatex.garmentms.repository.InventoryItemRepository;
import com.lankatex.garmentms.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryItemRepository inventoryItemRepository;

    @Override
    public InventoryItem createInventoryItem(String itemName, String itemCode,
                                             Integer quantity, Double unitPrice, String description) {
        if (inventoryItemRepository.existsByItemCode(itemCode)) {
            throw new IllegalArgumentException("Item code already exists: " + itemCode);
        }

        InventoryItem item = new InventoryItem();
        item.setItemName(itemName);
        item.setItemCode(itemCode);
        item.setQuantity(quantity);
        item.setUnitPrice(unitPrice);
        item.setDescription(description);

        return inventoryItemRepository.save(item);
    }

    @Override
    public InventoryItem updateInventoryItem(Long id, String itemName, String itemCode,
                                             Integer quantity, Double unitPrice, String description) {
        InventoryItem item = findInventoryItemById(id);

        // Check if item code is being changed to an existing code
        if (!item.getItemCode().equals(itemCode) && inventoryItemRepository.existsByItemCode(itemCode)) {
            throw new IllegalArgumentException("Item code already exists: " + itemCode);
        }

        item.setItemName(itemName);
        item.setItemCode(itemCode);
        item.setQuantity(quantity);
        item.setUnitPrice(unitPrice);
        item.setDescription(description);

        return inventoryItemRepository.save(item);
    }

    @Override
    public void deleteInventoryItem(Long id) {
        if (!inventoryItemRepository.existsById(id)) {
            throw new IllegalArgumentException("Inventory item not found with id: " + id);
        }
        inventoryItemRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryItem findInventoryItemById(Long id) {
        return inventoryItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inventory item not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InventoryItem> listAllInventoryItems(Pageable pageable) {
        return inventoryItemRepository.findAll(pageable);
    }

    @Override
    public void updateStockQuantity(Long itemId, Integer changeQuantity) {
        InventoryItem item = findInventoryItemById(itemId);
        int newQuantity = item.getQuantity() + changeQuantity;

        if (newQuantity < 0) {
            throw new IllegalArgumentException("Insufficient stock. Current: " + item.getQuantity());
        }

        item.setQuantity(newQuantity);
        inventoryItemRepository.save(item);
    }
}