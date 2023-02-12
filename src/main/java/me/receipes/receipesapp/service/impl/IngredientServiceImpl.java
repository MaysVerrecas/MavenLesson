package me.receipes.receipesapp.service.impl;

import me.receipes.receipesapp.exceptions.ValidateException;
import me.receipes.receipesapp.model.Ingredient;
import me.receipes.receipesapp.service.IngredientService;
import me.receipes.receipesapp.service.VoidCheckService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static long ingredientId = 0;
    private final Map<Long, Ingredient> ingredientMap = new TreeMap<>();
    private final VoidCheckService voidCheckService;

    public IngredientServiceImpl(VoidCheckService voidCheckService) {
        this.voidCheckService = voidCheckService;
    }


    @Override
    public long addIngredient(Ingredient ingredient) {
        if (!voidCheckService.validateIngredient(ingredient)) {
            throw new ValidateException(ingredient.toString());
        }
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
        ingredientMap.clear();
    }

    @Override
    public Map<Long, Ingredient> showAllIngredient() {
        return ingredientMap;
    }
}
