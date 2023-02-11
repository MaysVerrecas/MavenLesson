package me.receipes.receipesapp.service.impl;

import me.receipes.receipesapp.model.Ingredient;
import me.receipes.receipesapp.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static long ingredientId = 0;
    private static Map<Long, Ingredient> ingredientMap = new TreeMap<>();

    @Override
    public long addIngredient(Ingredient ingredient) {

        ingredientMap.put(ingredientId, ingredient);
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
        ingredientMap = new TreeMap<>();
    }

    @Override
    public Map<Long, Ingredient> showAllIngredient() {
        return ingredientMap;
    }
}
