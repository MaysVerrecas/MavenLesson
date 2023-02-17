package me.receipes.receipesapp.service;

import me.receipes.receipesapp.model.Recipe;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface RecipeService {
    long addRecipe(Recipe recipe);

    Recipe getRecipe(long recipeId);

    Map<Long, Recipe> showAllRecipes();

    Recipe changeRecipe(long recipeId, Recipe newRecipe);

    Recipe deleteRecipe(long recipeId);

    void deleteAllRecipes();

    Path createRecipeFileByTemplate() throws IOException;
}
