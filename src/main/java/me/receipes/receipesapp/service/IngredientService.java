package me.receipes.receipesapp.service;

import me.receipes.receipesapp.model.Ingredient;

import java.util.Map;

public interface IngredientService {
    long addIngredient(Ingredient ingredient);

    Ingredient getInredient(long ingredientId);

    Ingredient changeIngredient(long ingredientId, Ingredient newIngredient);

    Ingredient deleteIngredient(long ingredientId);

    void deleteAllIngredients();

    Map<Long, Ingredient> showAllIngredient();
}
