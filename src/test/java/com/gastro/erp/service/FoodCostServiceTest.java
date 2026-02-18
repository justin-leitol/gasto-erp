package com.gastro.erp.service;

import com.gastro.erp.dto.FoodCostKPI;
import com.gastro.erp.dto.RecipeIngredientRequest;
import com.gastro.erp.dto.RecipeRequest;
import com.gastro.erp.model.Ingredient;
import com.gastro.erp.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class FoodCostServiceTest {

    @Autowired
    private FoodCostService foodCostService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private IngredientService ingredientService;

    private Recipe testRecipe;
    private Ingredient testIngredient1;
    private Ingredient testIngredient2;

    @BeforeEach
    public void setUp() {
        // Create test ingredients
        testIngredient1 = new Ingredient();
        testIngredient1.setName("Test Flour");
        testIngredient1.setUnitOfMeasure("kg");
        testIngredient1.setUnitCost(new BigDecimal("2.00"));
        testIngredient1.setStockQuantity(new BigDecimal("100"));
        testIngredient1 = ingredientService.createIngredient(testIngredient1);

        testIngredient2 = new Ingredient();
        testIngredient2.setName("Test Sugar");
        testIngredient2.setUnitOfMeasure("kg");
        testIngredient2.setUnitCost(new BigDecimal("3.00"));
        testIngredient2.setStockQuantity(new BigDecimal("50"));
        testIngredient2 = ingredientService.createIngredient(testIngredient2);

        // Create test recipe
        RecipeRequest recipeRequest = new RecipeRequest();
        recipeRequest.setName("Test Cake");
        recipeRequest.setServings(8);
        recipeRequest.setSellingPrice(new BigDecimal("20.00"));
        testRecipe = recipeService.createRecipe(recipeRequest);

        // Add ingredients to recipe
        RecipeIngredientRequest ingredient1 = new RecipeIngredientRequest();
        ingredient1.setIngredientId(testIngredient1.getId());
        ingredient1.setQuantity(new BigDecimal("1.0")); // 1kg flour at $2.00
        recipeService.addIngredientToRecipe(testRecipe.getId(), ingredient1);

        RecipeIngredientRequest ingredient2 = new RecipeIngredientRequest();
        ingredient2.setIngredientId(testIngredient2.getId());
        ingredient2.setQuantity(new BigDecimal("0.5")); // 0.5kg sugar at $3.00
        recipeService.addIngredientToRecipe(testRecipe.getId(), ingredient2);
    }

    @Test
    public void testCalculateRecipeCost() {
        FoodCostKPI kpi = foodCostService.calculateRecipeCost(testRecipe.getId());

        assertNotNull(kpi);
        assertEquals(testRecipe.getId(), kpi.getRecipeId());
        assertEquals("Test Cake", kpi.getRecipeName());
        
        // Total cost: (1kg * $2.00) + (0.5kg * $3.00) = $3.50
        assertEquals(0, new BigDecimal("3.50").compareTo(kpi.getTotalCost()));
        
        // Cost per serving: $3.50 / 8 servings = $0.44 (rounded)
        assertTrue(kpi.getCostPerServing().compareTo(new BigDecimal("0.43")) >= 0);
        assertTrue(kpi.getCostPerServing().compareTo(new BigDecimal("0.45")) <= 0);
        
        assertEquals(8, kpi.getServings());
        assertEquals(0, new BigDecimal("20.00").compareTo(kpi.getSellingPrice()));
    }

    @Test
    public void testCalculateFoodCostPercentage() {
        BigDecimal percentage = foodCostService.calculateFoodCostPercentage(testRecipe.getId());

        assertNotNull(percentage);
        // Cost per serving ~$0.44, Selling price $20.00
        // Percentage = 0.44 / 20.00 * 100 = ~2.2%
        assertTrue(percentage.compareTo(new BigDecimal("2.0")) >= 0);
        assertTrue(percentage.compareTo(new BigDecimal("2.5")) <= 0);
    }

    @Test
    public void testCalculateAllRecipeCosts() {
        var allKpis = foodCostService.calculateAllRecipeCosts();

        assertNotNull(allKpis);
        assertTrue(allKpis.size() >= 1);
        
        FoodCostKPI kpi = allKpis.stream()
                .filter(k -> k.getRecipeId().equals(testRecipe.getId()))
                .findFirst()
                .orElse(null);
        
        assertNotNull(kpi);
        assertEquals("Test Cake", kpi.getRecipeName());
    }
}
