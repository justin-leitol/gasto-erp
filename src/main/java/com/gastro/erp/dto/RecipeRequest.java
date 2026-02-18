package com.gastro.erp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequest {
    private String name;
    private String description;
    private Integer servings;
    private String instructions;
    private BigDecimal sellingPrice;
}
