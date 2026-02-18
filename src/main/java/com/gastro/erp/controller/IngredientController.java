package com.gastro.erp.controller;

import com.gastro.erp.dto.InventoryStatus;
import com.gastro.erp.model.Ingredient;
import com.gastro.erp.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
@Tag(name = "Ingredients", description = "Ingredient management APIs")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping
    @Operation(summary = "Create a new ingredient")
    public ResponseEntity<Ingredient> createIngredient(@Valid @RequestBody Ingredient ingredient) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientService.createIngredient(ingredient));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an ingredient")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Long id, @Valid @RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ingredientService.updateIngredient(id, ingredient));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ingredient by ID")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.getIngredientById(id));
    }

    @GetMapping
    @Operation(summary = "Get all ingredients")
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an ingredient")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Get ingredients with low stock")
    public ResponseEntity<List<Ingredient>> getLowStockIngredients() {
        return ResponseEntity.ok(ingredientService.getLowStockIngredients());
    }

    @GetMapping("/inventory/status")
    @Operation(summary = "Get inventory status for all ingredients")
    public ResponseEntity<List<InventoryStatus>> getInventoryStatus() {
        return ResponseEntity.ok(ingredientService.getInventoryStatus());
    }

    @GetMapping("/inventory/low-stock")
    @Operation(summary = "Get inventory status for low stock items")
    public ResponseEntity<List<InventoryStatus>> getLowStockStatus() {
        return ResponseEntity.ok(ingredientService.getLowStockStatus());
    }
}
