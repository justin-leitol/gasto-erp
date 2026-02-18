package com.gastro.erp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryStatus {
    private Long ingredientId;
    private String ingredientName;
    private BigDecimal currentStock;
    private BigDecimal minimumStock;
    private String unitOfMeasure;
    private boolean lowStock;
    private BigDecimal unitCost;
    private BigDecimal totalValue;
}
