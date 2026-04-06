package com.lankatex.garmentms.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "inventory_item")
@Getter
@Setter
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String itemName;

    @Column(nullable = false, unique = true)
    private String itemCode;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double unitPrice;

    private String description;

    // Remove the duplicate setItemCode method - Lombok @Setter already generates it
}