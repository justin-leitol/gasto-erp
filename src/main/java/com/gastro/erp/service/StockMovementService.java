package com.gastro.erp.service;

import com.gastro.erp.dto.StockMovementRequest;
import com.gastro.erp.model.Ingredient;
import com.gastro.erp.model.Recipe;
import com.gastro.erp.model.StockMovement;
import com.gastro.erp.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StockMovementService {

    private final StockMovementRepository stockMovementRepository;
    private final IngredientService ingredientService;
    private final RecipeService recipeService;

    public StockMovement recordStockMovement(StockMovementRequest request) {
        Ingredient ingredient = ingredientService.getIngredientById(request.getIngredientId());
        
        StockMovement movement = new StockMovement();
        movement.setIngredient(ingredient);
        movement.setMovementType(StockMovement.MovementType.valueOf(request.getMovementType()));
        movement.setQuantity(request.getQuantity());
        movement.setPreviousStock(ingredient.getStockQuantity());
        movement.setReason(request.getReason());
        movement.setNotes(request.getNotes());
        movement.setPerformedBy(request.getPerformedBy());
        
        if (request.getRelatedRecipeId() != null) {
            Recipe recipe = recipeService.getRecipeById(request.getRelatedRecipeId());
            movement.setRelatedRecipe(recipe);
        }
        
        // Calculate new stock based on movement type
        BigDecimal stockChange = request.getQuantity();
        if (movement.getMovementType() == StockMovement.MovementType.CONSUMPTION ||
            movement.getMovementType() == StockMovement.MovementType.WASTE ||
            movement.getMovementType() == StockMovement.MovementType.RETURN) {
            stockChange = stockChange.negate();
        }
        
        // Update ingredient stock
        ingredientService.updateStock(ingredient.getId(), stockChange);
        
        // Get updated stock
        ingredient = ingredientService.getIngredientById(ingredient.getId());
        movement.setNewStock(ingredient.getStockQuantity());
        
        return stockMovementRepository.save(movement);
    }

    @Transactional(readOnly = true)
    public List<StockMovement> getAllMovements() {
        return stockMovementRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<StockMovement> getMovementsByIngredient(Long ingredientId) {
        return stockMovementRepository.findByIngredient_IdOrderByCreatedAtDesc(ingredientId);
    }

    @Transactional(readOnly = true)
    public List<StockMovement> getMovementsByType(StockMovement.MovementType movementType) {
        return stockMovementRepository.findByMovementType(movementType);
    }

    @Transactional(readOnly = true)
    public List<StockMovement> getMovementsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return stockMovementRepository.findMovementsBetweenDates(startDate, endDate);
    }

    public void recordRecipeProduction(Long recipeId, Integer quantity, String performedBy) {
        Recipe recipe = recipeService.getRecipeWithIngredients(recipeId);
        
        for (var recipeIngredient : recipe.getRecipeIngredients()) {
            BigDecimal totalQuantity = recipeIngredient.getQuantity().multiply(BigDecimal.valueOf(quantity));
            
            StockMovementRequest request = new StockMovementRequest();
            request.setIngredientId(recipeIngredient.getIngredient().getId());
            request.setMovementType(StockMovement.MovementType.CONSUMPTION.name());
            request.setQuantity(totalQuantity);
            request.setReason("Recipe production: " + recipe.getName() + " x" + quantity);
            request.setPerformedBy(performedBy);
            request.setRelatedRecipeId(recipeId);
            
            recordStockMovement(request);
        }
    }
}
