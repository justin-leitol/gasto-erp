package com.gastro.erp.repository;

import com.gastro.erp.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    
    Optional<Ingredient> findByName(String name);
    
    @Query("SELECT i FROM Ingredient i WHERE i.stockQuantity <= i.minimumStock")
    List<Ingredient> findLowStockIngredients();
    
    @Query("SELECT i FROM Ingredient i WHERE i.stockQuantity < :threshold")
    List<Ingredient> findIngredientsWithStockBelow(BigDecimal threshold);
    
    List<Ingredient> findBySupplier_Id(Long supplierId);
}
