package com.gastro.erp.repository;

import com.gastro.erp.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    
    List<RecipeIngredient> findByRecipe_Id(Long recipeId);
    
    List<RecipeIngredient> findByIngredient_Id(Long ingredientId);
}
