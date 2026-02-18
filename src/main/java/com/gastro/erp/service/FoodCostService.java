package com.gastro.erp.service;

import com.gastro.erp.dto.FoodCostKPI;
import com.gastro.erp.model.Recipe;
import com.gastro.erp.model.RecipeIngredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodCostService {

    private final RecipeService recipeService;

    public FoodCostKPI calculateRecipeCost(Long recipeId) {
        Recipe recipe = recipeService.getRecipeWithIngredients(recipeId);
        return calculateKPI(recipe);
    }

    public List<FoodCostKPI> calculateAllRecipeCosts() {
        List<Recipe> recipes = recipeService.getAllRecipesWithIngredients();
        return recipes.stream()
                .map(this::calculateKPI)
                .collect(Collectors.toList());
    }

    private FoodCostKPI calculateKPI(Recipe recipe) {
        BigDecimal totalCost = calculateTotalCost(recipe);
        BigDecimal costPerServing = totalCost.divide(
                BigDecimal.valueOf(recipe.getServings()), 
                2, 
                RoundingMode.HALF_UP
        );
        
        BigDecimal sellingPrice = recipe.getSellingPrice() != null ? recipe.getSellingPrice() : BigDecimal.ZERO;
        BigDecimal grossProfit = sellingPrice.subtract(costPerServing);
        
        BigDecimal profitMargin = BigDecimal.ZERO;
        if (sellingPrice.compareTo(BigDecimal.ZERO) > 0) {
            profitMargin = grossProfit.divide(sellingPrice, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }
        
        return new FoodCostKPI(
                recipe.getId(),
                recipe.getName(),
                totalCost,
                costPerServing,
                sellingPrice,
                grossProfit,
                profitMargin,
                recipe.getServings()
        );
    }

    private BigDecimal calculateTotalCost(Recipe recipe) {
        return recipe.getRecipeIngredients().stream()
                .map(this::calculateIngredientCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateIngredientCost(RecipeIngredient recipeIngredient) {
        return recipeIngredient.getQuantity()
                .multiply(recipeIngredient.getIngredient().getUnitCost());
    }

    public BigDecimal calculateFoodCostPercentage(Long recipeId) {
        FoodCostKPI kpi = calculateRecipeCost(recipeId);
        if (kpi.getSellingPrice().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return kpi.getCostPerServing()
                .divide(kpi.getSellingPrice(), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}
