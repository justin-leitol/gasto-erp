package com.gastro.erp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockMovementRequest {
    private Long ingredientId;
    private String movementType;
    private BigDecimal quantity;
    private String reason;
    private String notes;
    private String performedBy;
    private Long relatedRecipeId;
}
