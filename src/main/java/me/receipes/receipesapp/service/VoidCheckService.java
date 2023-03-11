package me.receipes.receipesapp.service;

import me.receipes.receipesapp.model.Ingredient;
import me.receipes.receipesapp.model.Recipe;

public interface VoidCheckService {

    boolean validateRecipe(Recipe recipe);

    boolean validateIngredient(Ingredient ingredient);
}
