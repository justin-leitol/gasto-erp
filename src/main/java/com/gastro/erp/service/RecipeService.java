package com.gastro.erp.service;

import com.gastro.erp.dto.RecipeIngredientRequest;
import com.gastro.erp.dto.RecipeRequest;
import com.gastro.erp.exception.ResourceNotFoundException;
import com.gastro.erp.model.Ingredient;
import com.gastro.erp.model.Recipe;
import com.gastro.erp.model.RecipeIngredient;
import com.gastro.erp.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;

    public Recipe createRecipe(RecipeRequest request) {
        Recipe recipe = new Recipe();
        recipe.setName(request.getName());
        recipe.setDescription(request.getDescription());
        recipe.setServings(request.getServings());
        recipe.setInstructions(request.getInstructions());
        recipe.setSellingPrice(request.getSellingPrice());
        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(Long id, RecipeRequest request) {
        Recipe existing = getRecipeById(id);
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setServings(request.getServings());
        existing.setInstructions(request.getInstructions());
        existing.setSellingPrice(request.getSellingPrice());
        return recipeRepository.save(existing);
    }

    @Transactional(readOnly = true)
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", id));
    }

    @Transactional(readOnly = true)
    public Recipe getRecipeWithIngredients(Long id) {
        return recipeRepository.findByIdWithIngredients(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", id));
    }

    @Transactional(readOnly = true)
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Recipe> getAllRecipesWithIngredients() {
        return recipeRepository.findAllWithIngredients();
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    public Recipe addIngredientToRecipe(Long recipeId, RecipeIngredientRequest request) {
        Recipe recipe = getRecipeById(recipeId);
        Ingredient ingredient = ingredientService.getIngredientById(request.getIngredientId());
        
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity(request.getQuantity());
        recipeIngredient.setNotes(request.getNotes());
        
        recipe.addIngredient(recipeIngredient);
        return recipeRepository.save(recipe);
    }

    public Recipe removeIngredientFromRecipe(Long recipeId, Long ingredientId) {
        Recipe recipe = getRecipeWithIngredients(recipeId);
        recipe.getRecipeIngredients().removeIf(ri -> ri.getIngredient().getId().equals(ingredientId));
        return recipeRepository.save(recipe);
    }
}
