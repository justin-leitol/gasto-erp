package com.gastro.erp.controller;

import com.gastro.erp.dto.FoodCostKPI;
import com.gastro.erp.service.FoodCostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/food-cost")
@RequiredArgsConstructor
@Tag(name = "Food Cost KPIs", description = "Food cost calculation and KPI APIs")
public class FoodCostController {

    private final FoodCostService foodCostService;

    @GetMapping("/recipe/{recipeId}")
    @Operation(summary = "Calculate food cost KPIs for a specific recipe")
    public ResponseEntity<FoodCostKPI> getRecipeCost(@PathVariable Long recipeId) {
        return ResponseEntity.ok(foodCostService.calculateRecipeCost(recipeId));
    }

    @GetMapping("/all-recipes")
    @Operation(summary = "Calculate food cost KPIs for all recipes")
    public ResponseEntity<List<FoodCostKPI>> getAllRecipeCosts() {
        return ResponseEntity.ok(foodCostService.calculateAllRecipeCosts());
    }

    @GetMapping("/recipe/{recipeId}/percentage")
    @Operation(summary = "Calculate food cost percentage for a recipe")
    public ResponseEntity<BigDecimal> getFoodCostPercentage(@PathVariable Long recipeId) {
        return ResponseEntity.ok(foodCostService.calculateFoodCostPercentage(recipeId));
    }
}
