package com.gastro.erp.repository;

import com.gastro.erp.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    
    List<StockMovement> findByIngredient_Id(Long ingredientId);
    
    List<StockMovement> findByIngredient_IdOrderByCreatedAtDesc(Long ingredientId);
    
    List<StockMovement> findByMovementType(StockMovement.MovementType movementType);
    
    @Query("SELECT sm FROM StockMovement sm WHERE sm.createdAt BETWEEN :startDate AND :endDate")
    List<StockMovement> findMovementsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT sm FROM StockMovement sm WHERE sm.ingredient.id = :ingredientId AND sm.createdAt BETWEEN :startDate AND :endDate")
    List<StockMovement> findIngredientMovementsBetweenDates(Long ingredientId, LocalDateTime startDate, LocalDateTime endDate);
    
    List<StockMovement> findByRelatedRecipe_Id(Long recipeId);
}
