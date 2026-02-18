package com.gastro.erp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodCostKPI {
    private Long recipeId;
    private String recipeName;
    private BigDecimal totalCost;
    private BigDecimal costPerServing;
    private BigDecimal sellingPrice;
    private BigDecimal grossProfit;
    private BigDecimal profitMargin;
    private Integer servings;
}
