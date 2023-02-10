package me.receipes.receipesapp.service;

import me.receipes.receipesapp.model.Recipe;

public interface RecipeService {
    void addRecipe(Recipe recipe);

    Recipe getRecipe(long recipeId);
}
