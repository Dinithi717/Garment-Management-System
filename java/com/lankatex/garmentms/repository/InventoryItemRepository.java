package com.lankatex.garmentms.repository;

import com.lankatex.garmentms.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Optional but recommended

import java.util.Optional;

@Repository // Optional: Helps Spring detect this as a repository bean
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    // Corrected: Use camelCase 'id' (matches entity property) and Long type (matches JpaRepository ID type)
    Optional<InventoryItem> findById(Long id);

    // Corrected: Use camelCase 'id' and Long type
    boolean existsById(Long id);

    // This method is already correct (assuming 'itemCode' is a String field in InventoryItem entity)
    boolean existsByItemCode(String itemCode);
}