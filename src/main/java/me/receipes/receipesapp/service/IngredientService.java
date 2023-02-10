package me.receipes.receipesapp.service;

import me.receipes.receipesapp.model.Ingredient;

public interface IngredientService {
    void addIngredient(Ingredient ingredient);

    Ingredient getInredient(long ingredientId);
}
