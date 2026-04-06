package com.lankatex.garmentms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInventoryItemRequest {
    @NotNull
    private Long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String itemName;

    @NotBlank
    @Size(min = 2, max = 50)
    private String itemCode;

    @NotNull
    @Min(0)
    private Integer quantity;

    @NotNull
    @Min(0)
    private Double unitPrice;

    private String description;
}