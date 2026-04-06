// FileName: MultipleFiles/LowStockAlertServiceImpl.java
package com.lankatex.garmentms.service.impl;

import com.lankatex.garmentms.model.InventoryItem;
import com.lankatex.garmentms.model.InventoryItem;
import com.lankatex.garmentms.model.LowStockAlert;
import com.lankatex.garmentms.repository.InventoryItemRepository;
import com.lankatex.garmentms.repository.InventoryItemRepository;
import com.lankatex.garmentms.repository.LowStockAlertRepository;
import com.lankatex.garmentms.service.LowStockAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LowStockAlertServiceImpl implements LowStockAlertService {

    private final LowStockAlertRepository lowStockAlertRepository;
    private final InventoryItemRepository itemRepository;

    @Override
    @Transactional
    public LowStockAlert createAlert(Long itemId, Integer currentStock, Integer reorderLevel) {
        InventoryItem item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + itemId));

        LowStockAlert alert = new LowStockAlert();
        alert.setItem(item);
        alert.setCurrentStock(currentStock);
        alert.setReorderLevel(reorderLevel);
        alert.setAlertDate(LocalDateTime.now());
        alert.setResolved(false);
        return lowStockAlertRepository.save(alert);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LowStockAlert> getUnresolvedAlerts() {
        return lowStockAlertRepository.findByResolvedFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LowStockAlert> listAllAlerts(Pageable pageable) {
        return lowStockAlertRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void resolveAlert(Long alertId) {
        LowStockAlert alert = lowStockAlertRepository.findById(alertId)
                .orElseThrow(() -> new IllegalArgumentException("Low stock alert not found with ID: " + alertId));
        alert.setResolved(true);
        lowStockAlertRepository.save(alert);
    }
}