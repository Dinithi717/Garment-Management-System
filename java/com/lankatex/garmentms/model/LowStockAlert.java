// FileName: MultipleFiles/LowStockAlert.java
package com.lankatex.garmentms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "lowstockalerts")
@Getter @Setter
public class LowStockAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false) // Changed 'id' to 'item_id'
    private InventoryItem item;

    @Column(nullable = false)
    private Integer currentStock;

    @Column(nullable = false)
    private Integer reorderLevel;

    @Column(nullable = false)
    private LocalDateTime alertDate;

    @Column(nullable = false)
    private boolean resolved = false; // To track if the alert has been addressed


}