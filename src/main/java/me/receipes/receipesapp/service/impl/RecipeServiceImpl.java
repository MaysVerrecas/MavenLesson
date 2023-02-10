package me.receipes.receipesapp.service.impl;

import me.receipes.receipesapp.model.Recipe;
import me.receipes.receipesapp.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class RecipeServiceImpl implements RecipeService {
    private static long recipeId = 0;
    private static Map<Long, Recipe> recipeMap;


    @Override
    public void addRecipe(Recipe recipe) {
        recipeMap.put(recipeId++, recipe);
    }

    @Override
    public Recipe getRecipe(long recipeId) {
        if (!recipeMap.containsKey(recipeId)) {
            throw new IllegalArgumentException("Рецепта под таким номером не существует!");
        }
        return recipeMap.get(recipeId);
    }
}
