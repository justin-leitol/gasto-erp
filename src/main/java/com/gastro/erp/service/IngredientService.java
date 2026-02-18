package com.gastro.erp.service;

import com.gastro.erp.dto.InventoryStatus;
import com.gastro.erp.model.Ingredient;
import com.gastro.erp.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Ingredient updateIngredient(Long id, Ingredient ingredient) {
        Ingredient existing = getIngredientById(id);
        existing.setName(ingredient.getName());
        existing.setDescription(ingredient.getDescription());
        existing.setUnitOfMeasure(ingredient.getUnitOfMeasure());
        existing.setUnitCost(ingredient.getUnitCost());
        existing.setMinimumStock(ingredient.getMinimumStock());
        existing.setSupplier(ingredient.getSupplier());
        return ingredientRepository.save(existing);
    }

    @Transactional(readOnly = true)
    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public void deleteIngredient(Long id) {
        ingredientRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Ingredient> getLowStockIngredients() {
        return ingredientRepository.findLowStockIngredients();
    }

    @Transactional(readOnly = true)
    public List<InventoryStatus> getInventoryStatus() {
        return ingredientRepository.findAll().stream()
                .map(this::convertToInventoryStatus)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<InventoryStatus> getLowStockStatus() {
        return ingredientRepository.findLowStockIngredients().stream()
                .map(this::convertToInventoryStatus)
                .collect(Collectors.toList());
    }

    private InventoryStatus convertToInventoryStatus(Ingredient ingredient) {
        BigDecimal totalValue = ingredient.getStockQuantity().multiply(ingredient.getUnitCost());
        boolean lowStock = ingredient.getMinimumStock() != null 
                && ingredient.getStockQuantity().compareTo(ingredient.getMinimumStock()) <= 0;
        
        return new InventoryStatus(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getStockQuantity(),
                ingredient.getMinimumStock(),
                ingredient.getUnitOfMeasure(),
                lowStock,
                ingredient.getUnitCost(),
                totalValue
        );
    }

    public void updateStock(Long ingredientId, BigDecimal quantity) {
        Ingredient ingredient = getIngredientById(ingredientId);
        ingredient.setStockQuantity(ingredient.getStockQuantity().add(quantity));
        ingredientRepository.save(ingredient);
    }
}
