package me.receipes.receipesapp.service.impl;

import me.receipes.receipesapp.exceptions.ValidateException;
import me.receipes.receipesapp.model.Recipe;
import me.receipes.receipesapp.service.RecipeService;
import me.receipes.receipesapp.service.VoidCheckService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static long recipeId = 0;
    private final Map<Long, Recipe> recipeMap = new TreeMap<>();
    private final VoidCheckService voidCheckService;

    public RecipeServiceImpl(VoidCheckService voidCheckService) {
        this.voidCheckService = voidCheckService;
    }


    @Override
    public long addRecipe(Recipe recipe) {
        if (!voidCheckService.validateRecipe(recipe)) {
            throw new ValidateException(recipe.toString());
        }
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
        recipeMap.clear();
    }
}
