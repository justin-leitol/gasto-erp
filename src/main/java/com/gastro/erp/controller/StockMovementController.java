package com.gastro.erp.controller;

import com.gastro.erp.dto.StockMovementRequest;
import com.gastro.erp.model.StockMovement;
import com.gastro.erp.service.StockMovementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
@RequiredArgsConstructor
@Tag(name = "Stock Movements", description = "Stock movement tracking and auditing APIs")
public class StockMovementController {

    private final StockMovementService stockMovementService;

    @PostMapping
    @Operation(summary = "Record a stock movement")
    public ResponseEntity<StockMovement> recordStockMovement(@Valid @RequestBody StockMovementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stockMovementService.recordStockMovement(request));
    }

    @GetMapping
    @Operation(summary = "Get all stock movements")
    public ResponseEntity<List<StockMovement>> getAllMovements() {
        return ResponseEntity.ok(stockMovementService.getAllMovements());
    }

    @GetMapping("/ingredient/{ingredientId}")
    @Operation(summary = "Get stock movements for a specific ingredient")
    public ResponseEntity<List<StockMovement>> getMovementsByIngredient(@PathVariable Long ingredientId) {
        return ResponseEntity.ok(stockMovementService.getMovementsByIngredient(ingredientId));
    }

    @GetMapping("/type/{movementType}")
    @Operation(summary = "Get stock movements by type")
    public ResponseEntity<List<StockMovement>> getMovementsByType(@PathVariable String movementType) {
        StockMovement.MovementType type = StockMovement.MovementType.valueOf(movementType.toUpperCase());
        return ResponseEntity.ok(stockMovementService.getMovementsByType(type));
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get stock movements between dates")
    public ResponseEntity<List<StockMovement>> getMovementsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(stockMovementService.getMovementsBetweenDates(startDate, endDate));
    }

    @PostMapping("/recipe-production/{recipeId}")
    @Operation(summary = "Record recipe production and consume ingredients")
    public ResponseEntity<Void> recordRecipeProduction(
            @PathVariable Long recipeId,
            @RequestParam Integer quantity,
            @RequestParam String performedBy) {
        stockMovementService.recordRecipeProduction(recipeId, quantity, performedBy);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
