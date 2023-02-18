package me.receipes.receipesapp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.receipes.receipesapp.exceptions.ValidateException;
import me.receipes.receipesapp.model.Ingredient;
import me.receipes.receipesapp.model.Recipe;
import me.receipes.receipesapp.service.FilesRecipeService;
import me.receipes.receipesapp.service.RecipeService;
import me.receipes.receipesapp.service.VoidCheckService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
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
    public void init() {

        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public String showIngredientsForSaveToFile(List<Ingredient> ingredients){
        String str = "";
        for (Ingredient ingredient : ingredients) {
            str += ("* " + ingredient.getName() + " - " + ingredient.getCount() + " " + ingredient.getMeasureUnit() + "\n");
        }
        return str + "\n";
    }

    public String showPreparingStepsForSaveToFile(List<Recipe.Step> steps){
        int counter = 0;
        String str = "";
        for (Recipe.Step step : steps) {
            counter++;
            str += ("" + counter + " " + step.getAction() + "\n");
        }
        return str + "\n";
    }

    @Override
    public Path createRecipeFileByTemplate() throws IOException {
        Path path = filesRecipeService.returnPath();
        try(Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)){
            for (Recipe recipe : recipeMap.values()) {
                writer.append("\n" + recipe.getName() + "\n" + "Время приготовления: " + recipe.getCookingTime() + "\n" + "Ингредиенты: " + "\n\n" +
                        showIngredientsForSaveToFile(recipe.getIngredients()) + "\n"  +
                        "Инструкция приготовления:" + "\n\n" + showPreparingStepsForSaveToFile(recipe.getCookingSteps()));
                writer.append("\n");
            }
        }
        return path;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class FileData{
        private long recipeId;
        private Map<Long, Recipe> recipes;
    }




}
