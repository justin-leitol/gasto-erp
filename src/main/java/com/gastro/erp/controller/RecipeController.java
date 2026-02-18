package com.gastro.erp.controller;

import com.gastro.erp.dto.RecipeIngredientRequest;
import com.gastro.erp.dto.RecipeRequest;
import com.gastro.erp.model.Recipe;
import com.gastro.erp.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Tag(name = "Recipes", description = "Recipe management APIs")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    @Operation(summary = "Create a new recipe")
    public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody RecipeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.createRecipe(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a recipe")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @Valid @RequestBody RecipeRequest request) {
        return ResponseEntity.ok(recipeService.updateRecipe(id, request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get recipe by ID")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    @GetMapping("/{id}/with-ingredients")
    @Operation(summary = "Get recipe with all ingredients")
    public ResponseEntity<Recipe> getRecipeWithIngredients(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipeWithIngredients(id));
    }

    @GetMapping
    @Operation(summary = "Get all recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    @GetMapping("/with-ingredients")
    @Operation(summary = "Get all recipes with ingredients")
    public ResponseEntity<List<Recipe>> getAllRecipesWithIngredients() {
        return ResponseEntity.ok(recipeService.getAllRecipesWithIngredients());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a recipe")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/ingredients")
    @Operation(summary = "Add ingredient to recipe")
    public ResponseEntity<Recipe> addIngredient(
            @PathVariable Long id, 
            @Valid @RequestBody RecipeIngredientRequest request) {
        return ResponseEntity.ok(recipeService.addIngredientToRecipe(id, request));
    }

    @DeleteMapping("/{recipeId}/ingredients/{ingredientId}")
    @Operation(summary = "Remove ingredient from recipe")
    public ResponseEntity<Recipe> removeIngredient(
            @PathVariable Long recipeId, 
            @PathVariable Long ingredientId) {
        return ResponseEntity.ok(recipeService.removeIngredientFromRecipe(recipeId, ingredientId));
    }
}
