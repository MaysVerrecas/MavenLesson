package me.receipes.receipesapp.service.impl;

import me.receipes.receipesapp.model.Ingredient;
import me.receipes.receipesapp.model.Recipe;
import me.receipes.receipesapp.service.VoidCheckService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
@Service
public class VoidCheckServiceImpl implements VoidCheckService {
    @Override
    public boolean validateRecipe(Recipe recipe) {
        return StringUtils.isNotBlank(recipe.getName())
                && !recipe.getIngredients().isEmpty()
                && !recipe.getCookingSteps().isEmpty();
    }

    @Override
    public boolean validateIngredient(Ingredient ingredient) {
        return StringUtils.isNotBlank(ingredient.getName())
                && StringUtils.isNotBlank(ingredient.getMeasureUnit());
    }
}
