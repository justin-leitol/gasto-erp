package com.gastro.erp.repository;

import com.gastro.erp.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    
    Optional<Recipe> findByName(String name);
    
    @Query("SELECT DISTINCT r FROM Recipe r JOIN FETCH r.recipeIngredients ri JOIN FETCH ri.ingredient WHERE r.id = :id")
    Optional<Recipe> findByIdWithIngredients(Long id);
    
    @Query("SELECT DISTINCT r FROM Recipe r JOIN FETCH r.recipeIngredients")
    List<Recipe> findAllWithIngredients();
}
