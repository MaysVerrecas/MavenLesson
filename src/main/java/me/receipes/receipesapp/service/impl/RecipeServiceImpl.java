package me.receipes.receipesapp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import me.receipes.receipesapp.exceptions.ValidateException;
import me.receipes.receipesapp.model.Recipe;
import me.receipes.receipesapp.service.FilesRecipeService;
import me.receipes.receipesapp.service.RecipeService;
import me.receipes.receipesapp.service.VoidCheckService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static long recipeId = 0;
    private TreeMap<Long, Recipe> recipeMap = new TreeMap<>();
    private final VoidCheckService voidCheckService;
    private final FilesRecipeService filesRecipeService;

    public RecipeServiceImpl(VoidCheckService voidCheckService, FilesRecipeService filesRecipeService) {
        this.voidCheckService = voidCheckService;
        this.filesRecipeService = filesRecipeService;
    }


    @PostConstruct
    private void init(){

        readFromFile();
    }
    @Override
    public long addRecipe(Recipe recipe) {
        if (!voidCheckService.validateRecipe(recipe)) {
            throw new ValidateException(recipe.toString());
        }
        recipeMap.put(recipeId, recipe);
        saveToFile();
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
    public TreeMap<Long, Recipe> showAllRecipes() {
        return recipeMap;
    }

    @Override
    public Recipe changeRecipe(long recipeId, Recipe newRecipe) {
        if (recipeMap.containsKey(recipeId)) {
            recipeMap.put(recipeId, newRecipe);
            saveToFile();
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


    private void readFromFile(){
        try {
            String json = filesRecipeService.readFromFile();
            recipeMap = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Long, Recipe>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            filesRecipeService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
