package me.receipes.receipesapp.service.impl;

import me.receipes.receipesapp.model.Ingredient;
import me.receipes.receipesapp.model.Recipe;
import me.receipes.receipesapp.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static long recipeId = 0;
    private static Map<Long, Recipe> recipeMap = new TreeMap<>();


    @Override
    public long addRecipe(Recipe recipe) {

        recipeMap.put(recipeId, recipe);
        return recipeId++;
    }

    @Override
    public Recipe getRecipe(long recipeId) {
        if (!recipeMap.containsKey(recipeId)) {
            return null;
        }
        return recipeMap.get(recipeId);
    }
    @Override
    public Map<Long, Recipe> showAllRecipes() {
        return recipeMap;
    }

    @Override
    public Recipe changeRecipe(long recipeId, Recipe newRecipe) {
        if (recipeMap.containsKey(recipeId)) {
            recipeMap.put(recipeId, newRecipe);
        }
        return null;
    }

    @Override
    public Recipe deleteRecipe(long recipeId) {
        if (recipeMap.containsKey(recipeId)) {
            return recipeMap.remove(recipeId);
        }
        return null;
    }

    @Override
    public void deleteAllRecipes() {
        recipeMap = new TreeMap<>();
    }
}
