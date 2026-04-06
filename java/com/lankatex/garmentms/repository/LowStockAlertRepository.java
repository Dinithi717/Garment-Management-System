// FileName: MultipleFiles/LowStockAlertRepository.java
package com.lankatex.garmentms.repository;

import com.lankatex.garmentms.model.LowStockAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LowStockAlertRepository extends JpaRepository<LowStockAlert, Long> {
    List<LowStockAlert> findByResolvedFalse(); // Find unresolved alerts
}