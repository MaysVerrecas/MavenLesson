package me.receipes.receipesapp.service.impl;

import me.receipes.receipesapp.model.Ingredient;
import me.receipes.receipesapp.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class IngredientServiceImpl implements IngredientService {
    private static long ingredientId = 0;
    private static Map<Long, Ingredient> ingredientMap;

    @Override
    public void addIngredient(Ingredient ingredient) {
        ingredientMap.put(ingredientId++, ingredient);
    }

    @Override
    public Ingredient getInredient(long ingredientId) {
        if (!ingredientMap.containsKey(ingredientId)) {
            throw new IllegalArgumentException("Ингриента с таким номером не существует!");
        }
        return ingredientMap.get(ingredientId);
    }
}
