package me.receipes.receipesapp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import me.receipes.receipesapp.exceptions.ValidateException;
import me.receipes.receipesapp.model.Ingredient;
import me.receipes.receipesapp.service.FilesIngredientService;
import me.receipes.receipesapp.service.IngredientService;
import me.receipes.receipesapp.service.VoidCheckService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static long ingredientId = 0;
    private TreeMap<Long, Ingredient> ingredientMap = new TreeMap<>();
    private final VoidCheckService voidCheckService;
    private final FilesIngredientService filesIngredientService;

    public IngredientServiceImpl(VoidCheckService voidCheckService, FilesIngredientService filesIngredientService) {
        this.voidCheckService = voidCheckService;
        this.filesIngredientService = filesIngredientService;
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
    public long addIngredient(Ingredient ingredient) {
        if (!voidCheckService.validateIngredient(ingredient)) {
            throw new ValidateException(ingredient.toString());
        }
        ingredientMap.put(ingredientId, ingredient);
        saveToFile();
        return ingredientId++;
    }

    @Override
    public Ingredient getInredient(long ingredientId) {
        if (ingredientMap.containsKey(ingredientId)) {
            return ingredientMap.get(ingredientId);
        }
        return null;
    }

    @Override
    public Ingredient changeIngredient(long ingredientId, Ingredient newIngredient) {
        if (ingredientMap.containsKey(ingredientId)) {
            ingredientMap.put(ingredientId, newIngredient);
            saveToFile();
        }
        return null;
    }

    @Override
    public Ingredient deleteIngredient(long ingredientId) {
        if (ingredientMap.containsKey(ingredientId)) {
            return ingredientMap.remove(ingredientId);
        }
        return null;
    }

    @Override
    public void deleteAllIngredients() {
        ingredientMap.clear();
    }

    @Override
    public TreeMap<Long, Ingredient> showAllIngredient() {
        return ingredientMap;
    }

    private void readFromFile() {
        try {
            String json = filesIngredientService.readFromFile();
            ingredientMap = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Long, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredientMap);
            filesIngredientService.saveToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
