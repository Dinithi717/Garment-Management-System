// FileName: MultipleFiles/LowStockAlertService.java
package com.lankatex.garmentms.service;

import com.lankatex.garmentms.model.LowStockAlert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LowStockAlertService {
    LowStockAlert createAlert(Long itemId, Integer currentStock, Integer reorderLevel);
    List<LowStockAlert> getUnresolvedAlerts();
    Page<LowStockAlert> listAllAlerts(Pageable pageable);
    void resolveAlert(Long alertId);
}